package cn.cerc.jmis.queue;

import org.apache.log4j.Logger;

import cn.cerc.jbean.client.AutoService;
import cn.cerc.jbean.client.LocalService;
import cn.cerc.jbean.rds.StubHandle;
import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jdb.core.TDateTime;
import cn.cerc.jmis.message.MessageProcess;
import cn.cerc.jmis.task.AbstractTask;

/**
 * 处理后台异步任务
 * 
 * @author ZhangGong
 *
 */
public class ProcessService extends AbstractTask {
	private static final Logger log = Logger.getLogger(ProcessService.class);

	@Override
	public void execute() {
		LocalService svr = new LocalService(this, "SvrUserMessages.getWaitList");
		if (!svr.exec())
			throw new RuntimeException(svr.getMessage());
		DataSet ds = svr.getDataOut();
		while (ds.fetch()) {
			log.info("开始处理异步任务，UID=" + ds.getString("UID_"));
			processService(ds.getString("UID_"));
		}
	}

	/**
	 * 处理一个服务
	 */
	private void processService(String msgId) {
		LocalService svr1 = new LocalService(this, "SvrUserMessages.readAsyncService");
		if (!svr1.exec("msgId", msgId)) // 此任务可能被其它主机抢占
			return;
		Record ds = svr1.getDataOut().getHead();
		String corpNo = ds.getString("corpNo");
		String userCode = ds.getString("userCode");
		String content = ds.getString("content");
		String subject = ds.getString("subject");

		// 读取并标识为工作中，以防被其它用户抢占
		AsyncService svr2 = new AsyncService(null);
		svr2.read(content);
		svr2.setProcess(MessageProcess.working.ordinal());
		updateMessage(svr2, msgId, subject);
		try {
			try (AutoService svr3 = new AutoService(corpNo, userCode, svr2.getService());) {
				svr3.getDataIn().appendDataSet(svr2.getDataIn(), true);
				if (svr3.exec()) {
					svr2.getDataOut().appendDataSet(svr3.getDataOut(), true);
					svr2.setProcess(MessageProcess.ok.ordinal());
				} else {
					svr2.getDataOut().appendDataSet(svr3.getDataOut(), true);
					svr2.setProcess(MessageProcess.error.ordinal());
				}
				svr2.getDataOut().getHead().setField("_message_", svr3.getMessage());
				updateMessage(svr2, msgId, subject);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			svr2.setProcess(MessageProcess.error.ordinal());
			svr2.getDataOut().getHead().setField("_message_", e.getMessage());
			updateMessage(svr2, msgId, subject);
		}
	}

	/**
	 * 保存到数据库
	 */
	private void updateMessage(AsyncService task, String msgId, String subject) {
		task.setProcessTime(TDateTime.Now().toString());
		LocalService svr = new LocalService(this, "SvrUserMessages.updateAsyncService");
		if (!svr.exec("msgId", msgId, "content", task.toString(), "process", task.getProcess()))
			throw new RuntimeException("更新任务队列进度异常：" + svr.getMessage());
		log.debug(task.getService() + ":" + subject + ":" + AsyncService.getProcessTitle(task.getProcess()));
	}

	// 手动执行所有的预约服务
	public static void main(String[] args) {
		StubHandle handle = new StubHandle();
		ProcessService ps = new ProcessService();
		ps.setHandle(handle);
		ps.run();
	}
}
