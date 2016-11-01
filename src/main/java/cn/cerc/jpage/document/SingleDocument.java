package cn.cerc.jpage.document;

import cn.cerc.jbean.form.IForm;
import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.form.FormView;

public class SingleDocument extends AbstractDocument {
	private Component body;

	public SingleDocument(IForm form, Component owner) {
		super(form, owner);
	}

	public FormView createForm() {
		FormView form = new FormView(this.getRequest());
		form.setId("search");
		form.setOwner(this);
		return form;
	}

	public Component createRightSide() {
		Component result = new Component(this);
		result.setId("rightSide");
		return result;
	}

	@Override
	public String getViewFile() {
		return "system/document.jsp";
	}

	public Component getBody() {
		if (body == null) {
			body = new Component();
			body.setOwner(this);
			body.setId("search");
		}
		return body;
	}

	public void setBody(Component body) {
		this.body = body;
	}
}
