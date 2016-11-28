package cn.cerc.jpage.form;

import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.common.HtmlWriter;

public class GoBackButton extends Component {

	public GoBackButton(Component owner) {
		super(owner);
		setId("goBack");
	}

	public void output(HtmlWriter html) {
		html.println("<a href=\"javascript:history.go(-1);\" id=\"%s\">", this.getId());
		html.println("<img src=\"images/goBack.png\"/>");
		html.println("</a>");
	}
}
