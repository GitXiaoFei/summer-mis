package cn.cerc.jmis.page;

import java.io.IOException;

import javax.servlet.ServletException;

import com.google.gson.Gson;

import cn.cerc.jbean.form.IForm;

public class JsonPage extends AbstractPage {
	private Object data;

	public JsonPage(IForm form) {
		super(form);
	}

	public JsonPage(IForm form, Object data) {
		super(form);
		this.data = data;
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
