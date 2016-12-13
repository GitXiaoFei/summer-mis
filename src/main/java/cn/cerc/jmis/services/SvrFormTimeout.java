package cn.cerc.jmis.services;

import cn.cerc.jbean.core.CustomService;
import cn.cerc.jbean.other.SystemTable;
import cn.cerc.jdb.core.Record;
import cn.cerc.jdb.core.TDateTime;
import cn.cerc.jdb.mysql.SqlQuery;

public class SvrFormTimeout extends CustomService {

	/**
	 * 
	 * 保存Form用户请求
	 */
	public boolean save() {
		Record headIn = getDataIn().getHead();
		SqlQuery ds = new SqlQuery(handle);
		ds.setMaximum(0);
		ds.add("select * from %s", SystemTable.get(SystemTable.getPageLogs));
		ds.open();
		ds.append();
		ds.setField("CorpNo_", this.getCorpNo());
		ds.setField("Page_", headIn.getSafeString("pageCode"));
		ds.setField("DataIn_", headIn.getSafeString("dataIn"));
		ds.setField("TickCount_", headIn.getDouble("tickCount"));
		ds.setField("AppUser_", this.getUserCode());
		ds.post();
		
		return true;
	}

	
}
