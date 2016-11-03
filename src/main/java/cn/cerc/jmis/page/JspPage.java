package cn.cerc.jmis.page;

import cn.cerc.jbean.form.IForm;

public class JspPage extends AbstractJspPage {

	public JspPage(IForm form) {
		super(form);
	}

	public JspPage(IForm form, String jspFile) {
		super(form);
		this.setJspFile(jspFile);
	}
}
