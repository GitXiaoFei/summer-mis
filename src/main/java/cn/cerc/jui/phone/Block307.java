package cn.cerc.jui.phone;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Span;

/**
 * 三行文字列表显示，右侧带导航栏箭头
 * <p>
 * 标题 + 3行信息说明
 * 
 * @author HuangRongjun
 *
 */
public class Block307 extends Component {
	private Span title;
	private UrlRecord url;
	private Image icon = new Image();
	private List<String> items = new ArrayList<>();

	public Block307(Component owner) {
		super(owner);
		title = new Span();
		title.setText("(title)");
		title.setRole("title");

		url = new UrlRecord();
		url.setName("(url)");

		icon.setSrc("jui/phone/block301-rightIcon.png");
		icon.setRole("right");
	}

	@Override
	public void output(HtmlWriter html) {
		if (items.size() == 0) {
			for (int i = 0; i < 3; i++) {
				items.add("line" + i);
			}
		}

		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block307'>");
		html.print("<a href='%s'>", url.getUrl());

		title.output(html);

		for (String line : items) {
			html.print("<div role='line'>%s</div>", line);
		}

		icon.output(html);

		html.print("</a>");
		html.print("</div>");
	}

	public Span getTitle() {
		return title;
	}

	public UrlRecord getUrl() {
		return url;
	}

	public void setUrl(UrlRecord url) {
		this.url = url;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public int size() {
		return items.size();
	}

	public void addItem(String line) {
		if (items.size() > 2) {
			throw new RuntimeException("最多只能放3行信息");
		}
		items.add(line);
	}
}
