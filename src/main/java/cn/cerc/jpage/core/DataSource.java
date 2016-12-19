package cn.cerc.jpage.core;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jdb.core.Record;

public interface DataSource {

	public void addField(IField field);

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
