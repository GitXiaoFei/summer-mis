package cn.cerc.jmis.page;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IPage;
import cn.cerc.jpage.core.HtmlWriter;

public class HtmlPage implements IPage {
	private IForm form;
	private HtmlWriter content = new HtmlWriter();

	public HtmlPage(IForm form) {
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

	@Override
	public void execute() throws ServletException, IOException {
		PrintWriter out = form.getResponse().getWriter();
		out.print(content.toString());
	}

	public HtmlWriter getContent() {
		return content;
	}

}
