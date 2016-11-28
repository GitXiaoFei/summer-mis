package cn.cerc.jmis.page;

import java.io.IOException;

import javax.servlet.ServletException;

import cn.cerc.jbean.form.IForm;

public class RedirectPage extends AbstractPage {
	private String url;

	public RedirectPage() {
	}

	public RedirectPage(IForm form) {
		super(form);
	}

	public RedirectPage(IForm form, String url) {
		super(form);
		this.url = url;
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
