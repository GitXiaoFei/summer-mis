package cn.cerc.jui.phone;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Span;

/**
 * 提示块
 * 
 * @author 郭向军
 *
 */
public class Block125 extends Component {
	private List<Span> items = new ArrayList<>();
	private List<UrlRecord> urlRecords = new ArrayList<>();

	public Block125(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block125'>");
		html.print("<ul>");
		for (int i = 0; i < items.size(); i++) {
			html.print("<li>");
			html.print("<a href='%s'>",
					this.urlRecords.get(i) == null ? "javascript:void(0);" : this.urlRecords.get(i).getUrl());
			items.get(i).output(html);
			html.print("</a>");
			html.print("</li>");
		}
		html.print("</ul>");
		html.print("</div>");
	}

	public List<Span> getItems() {
		return items;
	}

	public void addItems(String text, UrlRecord url) {
		Span span = new Span();
		span.setText(text);
		this.items.add(span);
		this.urlRecords.add(url);
	}

	public List<UrlRecord> getUrlRecords() {
		return urlRecords;
	}

	public void setUrlRecord(List<UrlRecord> urlRecords) {
		this.urlRecords = urlRecords;
	}

}
