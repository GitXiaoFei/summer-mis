package cn.cerc.jui.phone;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Span;

/**
 * 三行文字列表显示
 * <p>
 * 标题 + 2行信息说明
 * 
 * @author HuangRongjun
 *
 */
public class Block306 extends Component {
	private Span title;
	private List<String> items = new ArrayList<>();

	public Block306(Component owner) {
		super(owner);
		title = new Span();
		title.setText("(title)");
		title.setRole("title");
	}

	@Override
	public void output(HtmlWriter html) {
		if (items.size() == 0) {
			for (int i = 0; i < 2; i++) {
				items.add("line" + i);
			}
		}

		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block306'>");

		title.output(html);

		for (String line : items) {
			html.print("<div role='line'>%s</div>", line);
		}
		html.print("</div>");
	}

	public Span getTitle() {
		return title;
	}

	public int size() {
		return items.size();
	}

	public void addItems(String line) {
		if (items.size() > 2) {
			throw new RuntimeException("最多只能放3行信息");
		}
		items.add(line);
	}
}
