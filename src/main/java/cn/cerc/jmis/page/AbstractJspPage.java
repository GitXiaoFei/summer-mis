package cn.cerc.jmis.page;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;

import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.other.MemoryBuffer;
import cn.cerc.jdb.other.utils;
import cn.cerc.jpage.common.Component;

public abstract class AbstractJspPage extends Component implements IJspPage {
	private String jspFile;
	private IForm form;

	public AbstractJspPage() {
		super();
	}

	public AbstractJspPage(IForm form) {
		super();
		this.setForm(form);
	}

	@Override
	public void setForm(IForm form) {
		this.form = form;
	}

	@Override
	public IForm getForm() {
		return form;
	}

	public void execute() throws ServletException, IOException {
		String url = String.format("/WEB-INF/%s/%s", Application.getConfig().getPathForms(), jspFile);
		getRequest().getServletContext().getRequestDispatcher(url).forward(getRequest(), getResponse());
	}

	@Override
	public String getJspFile() {
		return jspFile;
	}

	@Override
	public void setJspFile(String jspFile) {
		this.jspFile = jspFile;
	}

	@Override
	public void add(String id, Object value) {
		getRequest().setAttribute(id, value);
	}

	@Override
	public final String getMessage() {
		return form.getParam("message", null);
	}

	@Override
	public final void setMessage(String message) {
		form.setParam("message", message);
	}

	@Override
	public String getViewFile() {
		return jspFile;
	}

	protected boolean fileExists(String fileName) {
		URL url = AbstractJspPage.class.getClassLoader().getResource("");
		if (url == null)
			return false;
		String filepath = url.getPath();
		String appPath = filepath.substring(0, filepath.indexOf("/WEB-INF"));
		String file = appPath + fileName;
		File f = new File(file);
		return f.exists();
	}

	// 从请求或缓存读取数据
	public String getValue(MemoryBuffer buff, String reqKey) {
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
		this.add(reqKey, result);
		return result;
	}

}
