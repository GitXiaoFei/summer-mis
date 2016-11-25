package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Label;
import cn.cerc.jpage.vcl.TextBox;

public class Block108 extends Component {
	private Label label = new Label();
	private TextBox input = new TextBox();

	/**
	 * 文本加输入框
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block108(Component owner) {
		super(owner);
		label.setText("(label)");
	}

	public void output(HtmlWriter html) {
		html.print("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block108'>");
		label.output(html);
		input.output(html);
		html.print("</div>");
	}

	public Label getLabel() {
		return label;
	}

	public TextBox getInput() {
		return input;
	}
}
