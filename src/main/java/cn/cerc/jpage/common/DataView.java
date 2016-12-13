package cn.cerc.jpage.common;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jdb.core.Record;
import cn.cerc.jui.vcl.columns.IColumn;

public interface DataView {

	public void addField(IColumn field);

	public Record getRecord();

	default public int getRecNo() {
		return 0;
	}

	default public void updateValue(String id, String code) {

	}

	default public boolean isReadonly() {
		return false;
	}

	default public HttpServletRequest getRequest() {
		return null;
	}
}
