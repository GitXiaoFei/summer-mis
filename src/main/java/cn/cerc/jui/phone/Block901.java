package cn.cerc.jui.phone;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Label;

public class Block901 extends Component {
	private List<Label> items = new ArrayList<>();

	/**
	 * 带图标的多行内容显示，如采购成功确认讯息显示
	 * 
	 * @param content
	 *            所在显示区域
	 */
	public Block901(Component content) {
		super(content);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block901'>");
		for (Label item : items)
			item.output(html);
		html.println("</div>");
	}

	public Label addLine(String text) {
		Label item = new Label();
		item.setText(text);
		items.add(item);
		return item;
	}
}
