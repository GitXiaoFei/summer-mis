package cn.cerc.jpage.document;

import cn.cerc.jbean.form.IForm;
import cn.cerc.jpage.common.Component;

public class CardDocument extends AbstractDocument {
	public CardDocument(IForm form, Component owner) {
		super(form, owner);
	}

	@Override
	public String getViewFile() {
		return "system/document-card.jsp";
	}
}
