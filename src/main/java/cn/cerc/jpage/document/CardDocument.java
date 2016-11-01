package cn.cerc.jpage.document;

import cn.cerc.jbean.form.IJspPage;

public class CardDocument extends AbstractDocument {
	public CardDocument(IJspPage owner) {
		super(owner);
	}

	@Override
	public void init() {
		this.getPage().setFile("system/document-card.jsp");
		this.addScriptFile("js/jquery-1.11.1.min.js");
		this.addScriptFile("js/delphi.vcl.js");
		this.addScriptFile("js/TApplication.js");
		this.addScriptFile("js/dialog.js");
		this.addScriptFile("js/Shopping.js");
	}
}
