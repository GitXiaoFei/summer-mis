package cn.cerc.jpage.document;

import cn.cerc.jbean.form.IJspPage;
import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.form.FormView;

public class SingleDocument extends AbstractDocument {
	private Component body;

	public SingleDocument(IJspPage owner) {
		super(owner);
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

	@Override
	public void init() {
		this.getPage().setFile("system/document.jsp");
	}
}
