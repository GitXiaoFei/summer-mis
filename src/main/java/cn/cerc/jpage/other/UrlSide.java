package cn.cerc.jpage.other;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;

public class UrlSide extends Component {

	private String title = "相关操作";
	private List<UrlRecord> urls = new ArrayList<>();
	private Map<String, String> items = new LinkedHashMap<>();

	public UrlSide() {
		super();
	}

	public UrlSide(Component owner) {
		super(owner);
	}

	public String getTitle() {
		return title;
	}

	public UrlSide setTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public void output(HtmlWriter html) {
		if (urls.size() == 0 && items.size() == 0)
			return;

		html.println("<section>");
		html.println("<div class=\"title\">%s</div>", this.title);
		html.println("<div class=\"contents\">");
		for (UrlRecord url : urls) {
			html.print("<a href=\"%s\"", url.getUrl());
			if (url.getTitle() != null) {
				html.print(" title=\"%s\"", url.getTitle());
			}
			if (url.getTarget() != null) {
				html.print(" target=\"%s\"", url.getTarget());
			}
			html.println(">%s</a>", url.getName());
		}
		for (String key : items.keySet())
			html.println("<a href=\"%s\">%s</a>", key, items.get(key));
		html.println("</div>");
		html.println("</section>");
	}

	public UrlRecord addUrl() {
		UrlRecord url = new UrlRecord();
		urls.add(url);
		return url;
	}

	public UrlRecord addUrl(UrlRecord url) {
		urls.add(url);
		return url;
	}

	public Map<String, String> getItems() {
		return items;
	}

	public void setItems(Map<String, String> items) {
		this.items = items;
	}
}
