package cn.cerc.jui.phone;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Span;

public class Block118 extends Component {
	private Span left = new Span();
	private List<UrlRecord> urls = new ArrayList<>();

	/**
	 * 文字 ：多个链接
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block118(Component owner) {
		super(owner);
		left.setText("(leftText)");
		left.setRole("left");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block118' role='row'>");
		left.output(html);
		if (urls.size() == 0) {
			UrlRecord url = addUrl();
			url.setName("(Url)");
		}
		for (UrlRecord url : urls) {
			html.print("<span role='right'>");
			html.print("<a href=\"%s\"", url.getUrl());
			if (url.getTitle() != null) {
				html.print(" title=\"%s\"", url.getTitle());
			}
			html.println(">%s</a>", url.getName());
			html.print("</span>");
		}
		html.println("</div>");
	}

	public Block118 setLeftText(String text) {
		left.setText(text);
		return this;
	}

	public UrlRecord addUrl() {
		UrlRecord url = new UrlRecord();
		urls.add(url);
		return url;
	}

}
