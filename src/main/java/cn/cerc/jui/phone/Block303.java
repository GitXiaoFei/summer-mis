package cn.cerc.jui.phone;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Image;

/**
 * 可用菜单 图标 + 文字
 * <p>
 * 一行最多占显示4个菜单
 * 
 * @author HuangRongjun
 *
 */
public class Block303 extends Component {
	private Map<UrlRecord, Image> items = new LinkedHashMap<>();

	public Block303(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		if (items.size() == 0) {
			for (int i = 0; i < 4; i++) {
				UrlRecord url = new UrlRecord();
				url.setName("(名称)");
				url.setSite("#");
				Image img = new Image();
				img.setSrc("jui/phone/block301-leftIcon.png");
				this.addItem(url, img);
			}
		}

		html.println("<!-- %s -->", this.getClass().getName());
		html.println("<div class='block303'>");
		for (UrlRecord url : items.keySet()) {
			html.println("<div role='item'>");
			html.println("<div role='image'>");
			html.println("<a href='%s'>%s</a>", url.getUrl(), items.get(url));
			html.println("</div>");
			html.println("<div role='title'>");
			html.println("<a href='%s'>%s</a>", url.getUrl(), url.getName());
			html.println("</div>");
			html.println("</div>");
		}
		html.println("</div>");
	}

	public void addItem(UrlRecord url, Image image) {
		if (items.size() > 3) {
			throw new RuntimeException("一个菜单组件最多容纳4个对象");
		}
		items.put(url, image);
	}

	public int size() {
		return items.size();
	}
}
