package cn.cerc.jpage.document;

import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.common.HtmlWriter;
import cn.cerc.jpage.form.UrlMenu;

public class CardItem extends Component {
	private String title;
	private UrlMenu url;

	public CardItem(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<section>");
		html.print("<label>");
		html.println(this.title);
		if (url != null)
			url.output(html);
		html.println("</label>");
		for (Component component : this.getComponents()) {
			html.print("<div>");
			component.output(html);
			html.print("</div>");
		}
		html.println("</section>");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UrlMenu getUrl() {
		if (url == null)
			url = new UrlMenu(null);
		return url;
	}

	public void setUrl(UrlMenu url) {
		this.url = url;
	}
}
