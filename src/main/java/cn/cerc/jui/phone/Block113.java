package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Span;
import cn.cerc.jpage.vcl.TextArea;

public class Block113 extends Component {
	private Span label = new Span();
	private TextArea input = new TextArea();

	/**
	 * 文本 + 长文本消息
	 * <p>
	 * 显示长文本信息，例如收货地址
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block113(Component owner) {
		super(owner);
		label.setText("(label)");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block113'>");
		label.output(html);
		input.output(html);
		html.println("</div>");
	}

	public Span getLabel() {
		return label;
	}

	public TextArea getInput() {
		return input;
	}

}
