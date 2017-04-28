package cn.cerc.jmis.page;

import java.io.IOException;

import javax.servlet.ServletException;

import com.google.gson.Gson;

import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IPage;

public class JsonPage implements IPage {
	//数据
	private Object data;
   //表单
	protected IForm form;

	public JsonPage() {
		super();
	}

	public JsonPage(IForm form) {
		super();
		this.setForm(form);
	}

	@Deprecated
	public JsonPage(IForm form, Object data) {
		super();
		this.setForm(form);
		this.data = data;
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
		getResponse().getWriter().print(new Gson().toJson(this.data));
	}

	public Object getData() {
		return data;
	}

	public JsonPage setData(Object data) {
		this.data = data;
		return this;
	}
}
