package cn.cerc.jmis.core;

import java.util.HashMap;
import java.util.Map;

import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IMenu;

public class FormMenu implements IMenu {
	private IForm form;
	private Map<String, String> params = new HashMap<>();

	public FormMenu() {

	}

	public IForm getForm() {
		return form;
	}

	public void setForm(IForm form) {
		this.form = form;
	}

	@Override
	public void setParam(String key, String value) {
		params.put(key, value);
	}

	@Override
	public String getParam(String key) {
		return params.get(key);
	}

}
