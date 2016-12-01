package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Button;
import cn.cerc.jpage.vcl.Label;
import cn.cerc.jpage.vcl.TextArea;

public class Block111 extends Component {
	private Label label = new Label();
	private TextArea input = new TextArea();
	private Button search = new Button();

	/**
	 * 文本 + 输入框 + 查询按钮
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block111(Component owner) {
		super(owner);
		label.setText("(label)");
		search.setText("查询");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block111'>");
		label.output(html);
		input.output(html);
		search.output(html);
		html.println("</div>");
	}

	public Label getLabel() {
		return label;
	}

	public TextArea getInput() {
		return input;
	}

	public Button getSearch() {
		return search;
	}
}
