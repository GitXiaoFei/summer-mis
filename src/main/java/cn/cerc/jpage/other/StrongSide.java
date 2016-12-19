package cn.cerc.jpage.other;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class StrongSide extends Component {
	private String title;
	private UrlMenu operaUrl;

	public StrongSide(Component owner) {
		super(owner);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<section>");
		html.print("<div class=\"title\">");
		html.print(this.title);
		if (operaUrl != null) {
			operaUrl.output(html);
		}
		html.println("</div>");
		html.println("<div class=\"contents\">");
		html.println("<ul>");
		for (Component component : getComponents()) {
			html.print("<li>");
			component.output(html);
			html.print("</li>");
		}
		html.println("</ul>");
		html.println("</div>");
		html.println("</section>");
	}

	public UrlMenu getOperaUrl() {
		if (operaUrl == null) {
			operaUrl = new UrlMenu(null);
			operaUrl.setStyle("float:right;line-height:1.25em;margin-bottom:0.25em");
		}
		return operaUrl;
	}

	public void setOperaUrl(UrlMenu operaUrl) {
		this.operaUrl = operaUrl;
	}
}
