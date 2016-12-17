package cn.cerc.jui.phone;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Span;

public class Block901 extends Component {
	private List<Span> items = new ArrayList<>();

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
		html.println("<!-- %s -->", this.getClass().getName());
		html.println("<div class='block901'>");
		for (Span item : items)
			item.output(html);
		html.println("</div>");
	}

	public Span addLine(String text) {
		Span item = new Span();
		item.setText(text);
		items.add(item);
		return item;
	}
}
