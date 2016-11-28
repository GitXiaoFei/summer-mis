package cn.cerc.jmis.page;

import java.io.IOException;

import javax.servlet.ServletException;

import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.form.IForm;

public class ErrorPage extends AbstractPage {
	private Throwable error;

	public ErrorPage(IForm form, Throwable error) {
		super(form);
		this.error = error;
	}

	@Override
	public void execute() throws ServletException, IOException {
		error.printStackTrace();
		String message = error.toString();
		getRequest().setAttribute("msg", message.substring(message.indexOf(":") + 1));
		String jspFile = Application.getConfig().getJspErrorFile();
		getRequest().getServletContext().getRequestDispatcher(jspFile).forward(getRequest(), getResponse());
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

}
