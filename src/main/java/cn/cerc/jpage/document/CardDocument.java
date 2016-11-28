package cn.cerc.jpage.document;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jpage.common.Component;

public class CardDocument extends CustomDocument {
	public CardDocument(Component owner, HttpServletRequest req) {
		super(owner, req);
	}

	@Override
	public String getViewFile() {
		return "system/document-card.jsp";
	}
}
