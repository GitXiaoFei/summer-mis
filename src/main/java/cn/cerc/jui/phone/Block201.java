package cn.cerc.jui.phone;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Image;

/**
 * 两行文字列表描述，右侧带导航栏箭头
 * 
 * @author HuangRongjun
 *
 */
public class Block201 extends Component {
	private List<String> items = new ArrayList<>();
	private Image icon = new Image();
	private UrlRecord url;

	public Block201(Component owner) {
		super(owner);
		url = new UrlRecord();
		icon.setSrc("jui/phone/block301-rightIcon.png");
		icon.setRole("right");
	}

	@Override
	public void output(HtmlWriter html) {
		if (items.size() == 0) {
			for (int i = 0; i < 2; i++) {
				items.add("line" + i);
			}
		}

		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block201'>");
		html.print("<a href='%s'>", url.getUrl());

		for (String line : items) {
			html.print("<div role='line'>%s</div>", line);
		}

		icon.output(html);

		html.print("</a>");
		html.print("</div>");
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

	public int size() {
		return items.size();
	}

	public void addItems(String line) {
		if (items.size() > 1) {
			throw new RuntimeException("最多只能放2行信息");
		}
		items.add(line);
	}
}
