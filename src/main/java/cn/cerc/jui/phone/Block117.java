package cn.cerc.jui.phone;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Span;

/**
 * 
 * @author 善贵
 *
 */
public class Block117 extends Component {
	private List<Span> addBlock = new ArrayList<>();

	/**
	 * 以span显示内容块
	 * 
	 * @param owner
	 *            内容显示区
	 * 
	 */
	public Block117(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block117'>");
		if (addBlock.size() == 0) {
			addBlock("(addBlock)");
			addBlock("(addBlock)");
		}
		for (Span span : addBlock)
			span.output(html);
		html.println("</div>");
	}

	public Span addBlock(String text) {
		Span span = new Span(this);
		span.setText(text);
		addBlock.add(span);
		return span;
	}

	public Span addBlock(String text, String onclick) {
		Span span = new Span(this);
		span.setText(text);
		span.setOnclick(onclick);
		addBlock.add(span);
		return span;
	}

}
