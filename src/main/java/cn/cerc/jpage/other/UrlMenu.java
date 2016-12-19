package cn.cerc.jpage.other;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class UrlMenu extends Component {
	private String name;
	private String url;
	private String cssClass;
	private String style;

	public UrlMenu(Component owner) {
		super(owner);
	}

	public UrlMenu(Component owner, String name, String url) {
		super(owner);
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public UrlMenu setName(String name) {
		this.name = name;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public UrlMenu setUrl(String url) {
		this.url = url;
		return this;
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<a href=\"%s\"", this.url);
		if (this.style != null)
			html.print(" style=\"%s\"", this.style);
		if (this.cssClass != null)
			html.print(" class=\"%s\"", this.cssClass);
		if (this.getId() != null)
			html.print(" id=\"%s\"", this.getId());

		html.println(">%s</a>", this.name);
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
}
