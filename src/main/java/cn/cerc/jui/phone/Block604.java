package cn.cerc.jui.phone;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Label;

/**
 * 用于卡片资料显示
 * <p>
 * 标题
 * <p>
 * 文字信息（最多6行）
 * 
 * @author HuangRongjun
 *
 */
public class Block604 extends Component {
	private Label title;
	private UrlRecord url;
	private Map<String, String> items = new LinkedHashMap<>();

	public Block604(Component owner) {
		super(owner);
		title = new Label();
		title.setCaption("(title)");
	}

	@Override
	public void output(HtmlWriter html) {
		if (items.size() == 0) {
			for (int i = 0; i < 7; i++) {
				items.put("(left)" + i, "(right)");
			}
		}

		html.println("<!-- %s -->", this.getClass().getName());
		html.println("<div class=\"block604\">");

		title.output(html);

		if (url != null) {
			html.print("<span role='url'>");
			html.print("<a href='%s'>%s</a>", url.getUrl(), url.getName());
			html.println("</span>");
		}

		for (String key : items.keySet()) {
			html.println("<div role='line'>%s：%s</div>", key, items.get(key));
		}
		html.println("</div>");
	}

	public Label getTitle() {
		return title;
	}

	public void setTitle(Label title) {
		this.title = title;
	}

	public UrlRecord getUrl() {
		return url;
	}

	public void setUrl(UrlRecord url) {
		this.url = url;
	}

	public void addItem(String left, String right) {
		if (items.size() > 5) {
			throw new RuntimeException("一个菜单组件最多容纳6个对象");
		}
		items.put(left, right);
	}

	public int size() {
		return items.size();
	}

}
