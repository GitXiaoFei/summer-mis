package cn.cerc.jmis.services;

import cn.cerc.jbean.core.CustomService;
import cn.cerc.jbean.other.SystemTable;
import cn.cerc.jdb.core.Record;
import cn.cerc.jdb.mysql.SqlQuery;

public class SvrBookInfo extends CustomService {

	public boolean getRecord() {
		String corpNo = getDataIn().getHead().getSafeString("corpNo");
		SqlQuery ds = new SqlQuery(handle);
		ds.add("select CorpNo_, Type_,ShortName_,Name_,Address_,Tel_,Status_,ManagerPhone_,StartHost_ "
				+ "from %s where CorpNo_=N'%s'", SystemTable.get(SystemTable.getBookInfo), corpNo);
		ds.open();
		if (ds.eof())
			return false;

		Record headOut = getDataOut().getHead();
		headOut.setField("corpNo", ds.getString("CorpNo_"));
		headOut.setField("shortName", ds.getString("ShortName_"));
		headOut.setField("name", ds.getString("Name_"));
		headOut.setField("address", ds.getString("Address_"));
		headOut.setField("tel", ds.getString("Tel_"));
		headOut.setField("managerPhone", ds.getString("ManagerPhone_"));
		headOut.setField("host", ds.getString("StartHost_"));

		headOut.setField("status", ds.getInt("Status_"));
		headOut.setField("type", ds.getInt("Type_"));
		return true;
	}
}
