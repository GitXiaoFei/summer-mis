package cn.cerc.jpage.form;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

@Deprecated
public class AdSide extends Component {
	private String adJpg = "images/easypic4-pc.jpg";

	public void output(HtmlWriter html) {
		html.println("<div class=\"ad\">");
		html.println("<div class=\"ban_javascript clear\">");
		html.println("<ul>");
		html.println("<li><img src=\"%s\"></li>", this.getAdJpg());
		html.println("</ul>");
		html.println("</div>");
		html.println("</div>");
	}

	public String getAdJpg() {
		return adJpg;
	}

	public void setAdJpg(String adJpg) {
		this.adJpg = adJpg;
	}
}
