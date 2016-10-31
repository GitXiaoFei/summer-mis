package cn.cerc.jmis.page;

import java.io.IOException;

import javax.servlet.ServletException;

import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IJspPage;
import cn.cerc.jbean.form.IMainForm;

public class JspPage extends AbstractPage implements IJspPage {
	private String jspFile;

	public JspPage(IForm form) {
		super(form);
	}

	public JspPage(IForm form, String jspFile) {
		super(form);
		this.setFile(jspFile);
	}

	public void execute() throws ServletException, IOException {
		String url;
		if (form != null) {
			IMainForm obj = Application.getMainPage();
			if (obj != null)
				obj.execute(form, this);
		}
		url = String.format("/WEB-INF/%s/%s", Application.getConfig().getPathForms(), jspFile);
		getRequest().getServletContext().getRequestDispatcher(url).forward(getRequest(), getResponse());
	}

	public String getJspFile() {
		return jspFile;
	}

	@Override
	public void setFile(String jspFile) {
		this.jspFile = jspFile;
	}

	@Override
	public void add(String id, Object value) {
		getRequest().setAttribute(id, value);
	}

	@Override
	public String getMessage() {
		return form.getParam("message", null);
	}

	@Override
	public void setMessage(String message) {
		form.setParam("message", message);
	}

	@Override
	public String getViewFile() {
		return jspFile;
	}

}
