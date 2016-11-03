package cn.cerc.jmis.page;

import java.io.IOException;

import javax.servlet.ServletException;

import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.form.IForm;
import cn.cerc.jpage.common.Component;

public abstract class AbstractJspPage extends Component implements IJspPage {
	private String jspFile;
	private IForm form;

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
