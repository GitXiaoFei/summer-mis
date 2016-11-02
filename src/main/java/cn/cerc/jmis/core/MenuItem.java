package cn.cerc.jmis.core;

import java.util.HashMap;
import java.util.Map;

import cn.cerc.jbean.form.IMenu;

public class MenuItem implements IMenu {
	private String id;
	private Map<String, String> params = new HashMap<>();

	public static final String ID = "formNo";
	public static final String TITLE = "title";
	public static final String SECURITY = "security";
	public static final String VERSIONS = "versions";
	public static final String PROCCODE = "procCode";

	public MenuItem() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setParam(String key, String value) {
		params.put(key, value);
	}

	@Override
	public String getParam(String key) {
		return params.get(key);
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

}
