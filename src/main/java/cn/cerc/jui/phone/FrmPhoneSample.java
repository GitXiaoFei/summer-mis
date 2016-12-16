package cn.cerc.jui.phone;

import cn.cerc.jbean.form.IPage;
import cn.cerc.jmis.form.AbstractForm;

public class FrmPhoneSample extends AbstractForm {

	@Override
	public IPage execute() throws Exception {
		PhonePage page = new PhonePage(this);

		new Block101(page.getContent());
		new Block102(page.getContent());
		new Block103(page.getContent());
		new Block104(page.getContent());
		new Block105(page.getContent());
		new Block112(page.getContent());
		new Block107(page.getContent());
		new Block118(page.getContent());

		new Block301(page.getContent());
		new Block302(page.getContent());
		new Block303(page.getContent());
		new Block304(page.getContent());
		new Block305(page.getContent());

		new Block401(page.getContent());
		new Block402(page.getContent());

		new Block901(page.getContent());
		new Block902(page.getContent());

		// new Block991(page.getContent());
		Block992 b992 = new Block992(page.getContent());
		b992.addButton("进入系统");
		b992.addButton("进入系统");
		b992.addButton("进入系统");
		b992.addButton("进入系统");
		return page;
	}

	@Override
	public boolean logon() {
		return true;
	}
}
