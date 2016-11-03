package cn.cerc.jmis.page;

import java.io.IOException;

import javax.servlet.ServletException;

import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IPage;

public class RedirectPage implements IPage {
	private String url;
	protected IForm form;

	public RedirectPage() {
		super();
	}

	public RedirectPage(IForm form) {
		super();
		this.setForm(form);
	}

	public RedirectPage(IForm form, String url) {
		super();
		this.setForm(form);
		this.url = url;
	}

	@Override
	public void setForm(IForm form) {
		this.form = form;
	}

	@Override
	public IForm getForm() {
		return form;
	}

	@Override
	public void execute() throws ServletException, IOException {
		getResponse().sendRedirect(url);
	}

	public String getUrl() {
		return url;
	}

	public RedirectPage setUrl(String url) {
		this.url = url;
		return this;
	}

}
