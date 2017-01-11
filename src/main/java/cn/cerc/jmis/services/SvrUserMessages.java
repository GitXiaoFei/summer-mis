package cn.cerc.jmis.services;

import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.core.CustomService;
import cn.cerc.jbean.other.BufferType;
import cn.cerc.jbean.other.SystemTable;
import cn.cerc.jdb.cache.IMemcache;
import cn.cerc.jdb.core.Record;
import cn.cerc.jdb.core.TDateTime;
import cn.cerc.jdb.mysql.SqlOperator;
import cn.cerc.jdb.mysql.SqlQuery;
import cn.cerc.jmis.message.MessageLevel;
import cn.cerc.jmis.message.MessageProcess;
import cn.cerc.jmis.message.MessageRecord;

//用户消息操作
public class SvrUserMessages extends CustomService {

	/**
	 * 
	 * @return 取出所有的等待处理的消息列表
	 */
	public boolean getWaitList() {
		SqlQuery ds = new SqlQuery(this);
		ds.setMaximum(5);
		ds.add("select ms.UID_ from %s ms", SystemTable.get(SystemTable.getUserMessages));
		ds.add("where ms.Level_=%s", MessageLevel.Service.ordinal());
		ds.add("and ms.Process_=%s", MessageProcess.wait.ordinal());
		ds.open();
		this.getDataOut().appendDataSet(ds);
		return true;
	}

	/**
	 * 
	 * @return 增加一条新的消息记录
	 */
	public boolean appendRecord() {
		Record headIn = getDataIn().getHead();
		String corpNo = headIn.getString("corpNo");
		String userCode = headIn.getString("userCode");
		String subject = headIn.getString("subject");
		String content = headIn.getString("content");
		int process = headIn.getInt("process");
		int level = headIn.getInt("level");

		// 若为异步任务消息请求
		if (level == MessageLevel.Service.ordinal()) {
			// 若已存在同一公司别同一种回算请求在排队或者执行中，则不重复插入回算请求
			SqlQuery ds2 = new SqlQuery(handle);
			ds2.setMaximum(1);
			ds2.add("select UID_ from %s ", SystemTable.get(SystemTable.getUserMessages));
			ds2.add("where CorpNo_='%s' ", corpNo);
			ds2.add("and Subject_='%s' ", subject);
			ds2.add("and Level_=4 and (Process_ = 1 or Process_=2)");
			ds2.open();
			if (ds2.size() > 0) {
				// 返回消息的编号
				getDataOut().getHead().setField("msgId", ds2.getInt("UID_"));
				return true;
			}
		}

		// 保存到数据库
		Record ds = new Record();
		ds.setField("CorpNo_", corpNo);
		ds.setField("UserCode_", userCode);
		ds.setField("Level_", level);
		ds.setField("Subject_", subject);
		if (content.length() > 0)
			ds.setField("Content_", content.toString());
		ds.setField("AppUser_", handle.getUserCode());
		ds.setField("AppDate_", TDateTime.Now());
		// 日志类消息默认为已读
		ds.setField("Status_", level == MessageLevel.Logger.ordinal() ? 1 : 0);
		ds.setField("Process_", process);
		ds.setField("Final_", false);
		ds.setField("UID_", null);

		SqlOperator operator = new SqlOperator(handle);
		operator.setTableName(SystemTable.get(SystemTable.getUserMessages));
		operator.insert(ds);

		// 清除缓存
		IMemcache buff = Application.getMemcache();
		String buffKey = String.format("%d.%s.%s.%s", BufferType.getObject.ordinal(), MessageRecord.class, corpNo,
				userCode);
		buff.delete(buffKey);

		// 返回消息的编号
		getDataOut().getHead().setField("msgId", ds.getInt("UID_"));
		return true;
	}

	/**
	 * 
	 * @return 读取指定的消息记录
	 */
	public boolean readAsyncService() {
		String msgId = getDataIn().getHead().getSafeString("msgId");

		SqlQuery ds = new SqlQuery(this);
		ds.add("select * from %s", SystemTable.get(SystemTable.getUserMessages));
		ds.add("where Level_=%s", MessageLevel.Service.ordinal());
		ds.add("and Process_=%s", MessageProcess.wait.ordinal());
		ds.add("and UID_='%s'", msgId);
		ds.open();
		if (ds.eof()) // 此任务可能被其它主机抢占
			return false;

		Record headOut = getDataOut().getHead();
		headOut.setField("corpNo", ds.getString("CorpNo_"));
		headOut.setField("userCode", ds.getString("UserCode_"));
		headOut.setField("subject", ds.getString("Subject_"));
		headOut.setField("content", ds.getString("Content_"));
		return true;
	}

	/**
	 * 
	 * @return 更新异步服务进度
	 */
	public boolean updateAsyncService() {
		String msgId = getDataIn().getHead().getSafeString("msgId");
		String content = getDataIn().getHead().getString("content");
		int process = getDataIn().getHead().getInt("process");

		SqlQuery ds = new SqlQuery(this);
		ds.add("select * from %s", SystemTable.get(SystemTable.getUserMessages));
		ds.add("where UID_='%s'", msgId);
		ds.open();
		if (ds.eof()) {
			// 此任务可能被其它主机抢占
			this.setMessage(String.format("消息号UID_ %s 不存在", msgId));
			return false;
		}
		ds.edit();
		ds.setField("Content_", content);
		ds.setField("Process_", process);
		ds.post();
		return true;
	}
}
