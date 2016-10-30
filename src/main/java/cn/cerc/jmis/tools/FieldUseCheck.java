package cn.cerc.jmis.tools;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jbean.core.AbstractHandle;
import cn.cerc.jdb.core.IHandle;
import cn.cerc.jdb.mysql.BuildQuery;

public class FieldUseCheck extends AbstractHandle {
	private List<String> items = new ArrayList<>();

	public FieldUseCheck(IHandle handle) {
		this.setHandle(handle);
	}

	public boolean exists(String fieldValue) {
		for (String key : items) {
			String[] args = key.split(":");
			if (checkTable(args[0], args[1], fieldValue))
				return true;
		}
		return false;
	}

	public FieldUseCheck add(String tableCode, String fieldCode) {
		items.add(tableCode + ":" + fieldCode);
		return this;
	}

	private boolean checkTable(String tableCode, String fieldCode, String fieldValue) {
		BuildQuery bq = new BuildQuery(this);
		bq.byField("CorpNo_", handle.getCorpNo());
		bq.byParam(String.format("%s=N'%s'", fieldCode, fieldValue));
		bq.setMaximum(1);
		bq.add("select %s * ");
		bq.add("from %s ", tableCode);
		bq.open();
		return !bq.getDataSet().eof();
	}
}
