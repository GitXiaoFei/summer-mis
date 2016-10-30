package cn.cerc.jmis.services;

import cn.cerc.jbean.core.CustomService;
import cn.cerc.jbean.other.SystemTable;
import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jdb.core.TDateTime;
import cn.cerc.jdb.mysql.BuildQuery;
import cn.cerc.jdb.mysql.SqlQuery;

public class SvrCustomMenus extends CustomService {

	public boolean append() {
		DataSet dataIn = getDataIn();
		String corpNo = dataIn.getHead().getSafeString("CorpNo_");
		BuildQuery f1 = new BuildQuery(this);
		BuildQuery f2 = new BuildQuery(this);

		f1.byField("Custom_", true);
		f1.add("select * from %s ", SystemTable.get(SystemTable.getAppMenus));
		SqlQuery ds1 = f1.open();

		f2.byField("CorpNo_", corpNo);
		f2.add("select * from %s ", SystemTable.get(SystemTable.getCustomMenus));
		SqlQuery ds2 = f2.open();

		while (!ds2.eof()) {
			if (!dataIn.locate("id", ds2.getString("ID_")))
				ds2.delete();
			else
				ds2.next();
		}

		while (dataIn.fetch()) {
			if (!ds1.locate("ID_", dataIn.getString("id")))
				throw new RuntimeException("菜单错误，请核查！");
			if (!ds2.locate("ID_", dataIn.getString("id"))) {
				ds1.locate("ID_", dataIn.getString("id"));

				ds2.append();
				ds2.setField("CorpNo_", corpNo);
				ds2.setField("Code_", ds1.getString("Code_"));
				ds2.setField("Name_", ds1.getString("Name_"));
				ds2.setField("AppUser_", getUserCode());
				ds2.setField("AppDate_", TDateTime.Now().getDate());
				ds2.setField("Remark_", "");
				ds2.post();
			}
		}
		return true;
	}

	public boolean list() {
		BuildQuery f = new BuildQuery(this);
		String corpNo = getDataIn().getHead().getSafeString("CorpNo_");
		if (corpNo == null || corpNo.equals(""))
			throw new RuntimeException("您输入的公司别为空，请检查！");
		f.byField("s.Custom_", true);
		// FIXME: 后续须把*改为具体字段
		f.add("select s.*,c.Code_ as CostomCode_ from %s s ", SystemTable.get(SystemTable.getAppMenus));
		f.add("left join %s c on s.Code_ = c.Code_ and c.CorpNo_='%s'", SystemTable.get(SystemTable.getCustomMenus),
				corpNo);
		getDataOut().appendDataSet(f.open());
		return true;
	}

	public boolean search() {
		if (!SystemTable.ManageBook.equals(handle.getCorpNo()))
			throw new RuntimeException("您不是运营商账号不允许操作！");

		Record headIn = getDataIn().getHead();
		BuildQuery f = new BuildQuery(this);
		f.byField("s.Custom_", true);
		if (headIn.exists("SearchText_"))
			f.byLink(new String[] { "s.Name_", "c.CorpNo_", "oi.ShortName_" }, headIn.getString("SearchText_"));
		if (headIn.exists("MaxRecord_"))
			f.setMaximum(headIn.getInt("MaxRecord_"));
		if (headIn.exists("Custom"))
			f.byParam("c.CorpNo_ !='' and c.CorpNo_ is Not null");
		f.add("select s.Code_,s.Name_,c.Code_ as CostomCode_,oi.ShortName_,c.Remark_,c.CorpNo_,c.AppUser_,c.AppDate_ ");
		f.add("from %s s ", SystemTable.get(SystemTable.getAppMenus));
		f.add("left join %s c on s.Code_ = c.Code_", SystemTable.get(SystemTable.getCustomMenus));
		f.add("left join %s oi on oi.CorpNo_ = c.CorpNo_", SystemTable.get(SystemTable.getBookInfo));
		getDataOut().appendDataSet(f.open());

		return true;
	}
}
