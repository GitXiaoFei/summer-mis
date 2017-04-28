package cn.cerc.jmis.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cerc.jbean.core.AbstractHandle;
import cn.cerc.jbean.core.CustomHandle;
import cn.cerc.jbean.form.IClient;
import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IMenu;
import cn.cerc.jmis.core.ClientDevice;

public abstract class AbstractForm extends AbstractHandle implements IForm {
	//请求
	private HttpServletRequest request;
	//响应
	private HttpServletResponse response;
	private IClient client;
	//菜单
	private IMenu menu;
	private Map<String, String> params = new HashMap<>();

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public void init(AbstractForm owner) {
		this.setHandle(owner.getHandle());
		this.setClient(owner.getClient());
		this.setRequest(owner.getRequest());
		this.setResponse(owner.getResponse());
	}

	@Override
	public boolean logon() {
		if (getHandle() == null)
			return false;
		CustomHandle sess = (CustomHandle) getHandle().getProperty(null);
		return sess.logon();
	}

	@Override
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public HttpServletRequest getRequest() {
		return this.request;
	}

	@Override
	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public Object getProperty(String key) {
		if ("request".equals(key))
			return this.getRequest();
		if ("session".equals(key))
			return this.getRequest().getSession();

		return handle.getProperty(key);
	}

	@Override
	public String getTitle() {
		String formNo = this.getParam("formNo", "000");
		String formCatpion = this.getParam("title", "");
		return String.format("%s(%s)", formCatpion, formNo);
	}

	@Override
	public IClient getClient() {
		if (client == null) {
			client = new ClientDevice(this);
		}
		return client;
	}

	@Override
	public void setClient(IClient client) {
		this.client = client;
	}

	@Override
	public void setParam(String key, String value) {
		params.put(key, value);
	}

	@Override
	public String getParam(String key, String def) {
		if (params.containsKey(key))
			return params.get(key);
		else {
			if (menu != null) {
				String result = menu.getParam(key);
				return result != null ? result : def;
			} else
				return def;
		}
	}

	@Override
	public IMenu getMenu() {
		return menu;
	}

	@Override
	public void setMenu(IMenu menu) {
		this.menu = menu;
	}
}
