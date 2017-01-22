package cn.cerc.jpage.core;

import cn.cerc.jdb.core.DataSet;

public interface DataSource {

	public void addField(IField field);

	public DataSet getDataSet();

	public boolean isReadonly();

	public void updateValue(String id, String code);
}
