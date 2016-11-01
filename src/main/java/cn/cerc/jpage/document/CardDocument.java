package cn.cerc.jpage.document;

import cn.cerc.jbean.form.IJspPage;

public class CardDocument extends AbstractDocument {
	public CardDocument(IJspPage owner) {
		super(owner);
	}

	@Override
	public void init() {
		this.getPage().setFile("system/document-card.jsp");
	}
}
