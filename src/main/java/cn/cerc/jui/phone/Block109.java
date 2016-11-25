package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Label;
import cn.cerc.jpage.vcl.TextBox;

public class Block109 extends Component {
	private Label label = new Label();
	private TextBox input = new TextBox();
	private Image select = new Image();

	/**
	 * 文本+输入框+弹窗选择按钮
	 * 
	 * @param owner
	 */
	public Block109(Component owner) {
		super(owner);
		label.setText("(label)");
		select.setSrc("jui/phone/block109-select.png");
	}

	public void output(HtmlWriter html) {
		html.print("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block109'>");
		label.output(html);
		input.output(html);
		select.output(html);
		html.print("</div>");
	}

	public Label getLabel() {
		return label;
	}

	public TextBox getInput() {
		return input;
	}

	public Image getSelect() {
		return select;
	}
}
