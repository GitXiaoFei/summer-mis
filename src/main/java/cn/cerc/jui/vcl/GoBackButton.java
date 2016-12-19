package cn.cerc.jui.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

@Deprecated
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
