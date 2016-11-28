package cn.cerc.jmis.page;

import java.io.IOException;

import javax.servlet.ServletException;

import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IPage;

public abstract class AbstractPage implements IPage {
	protected IForm form;

	public AbstractPage() {
		super();
	}

	public AbstractPage(IForm page) {
		super();
		this.setForm(page);
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
	public abstract void execute() throws ServletException, IOException;
}
