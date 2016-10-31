package cn.cerc.jmis.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cerc.jbean.core.AbstractHandle;
import cn.cerc.jbean.core.CustomHandle;
import cn.cerc.jbean.form.IClient;
import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IJspPage;
import cn.cerc.jbean.other.MemoryBuffer;
import cn.cerc.jdb.other.utils;
import cn.cerc.jmis.core.ClientDevice;

public abstract class AbstractForm extends AbstractHandle implements IForm {
	private MainMenu mainMenu;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private IClient client;
	private Map<String, String> params = new HashMap<>();

	public void init(AbstractForm owner) {
		this.setMainMenu(owner.getMainMenu());
		this.setHandle(owner.getHandle());
		this.setClient(owner.getClient());
		this.setRequest(owner.getRequest());
		this.setResponse(owner.getResponse());
	}

	// 从请求或缓存读取数据
	protected String getValue(IJspPage page, MemoryBuffer buff, String reqKey) {
		String result = getRequest().getParameter(reqKey);
		if (result == null) {
			String val = buff.getString(reqKey).replace("{}", "");
			if (utils.isNumeric(val) && val.endsWith(".0"))
				result = val.substring(0, val.length() - 2);
			else
				result = val;
		} else {
			result = result.trim();
			buff.setField(reqKey, result);
		}
		page.add(reqKey, result);
		return result;
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
		getClient().setRequest(request);
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

	public MainMenu getMainMenu() {
		if (mainMenu == null)
			mainMenu = new MainMenu();
		return mainMenu;
	}

	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	@Override
	public IClient getClient() {
		if (client == null) {
			client = new ClientDevice();
			client.setRequest(getRequest());
		}
		return client;
	}

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
		else
			return def;
	}
}
