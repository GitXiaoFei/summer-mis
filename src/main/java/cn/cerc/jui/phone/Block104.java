package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Button;
import cn.cerc.jpage.vcl.TextBox;

public class Block104 extends Component {
	private TextBox input;
	private Button submit;

	/**
	 * 通用搜索条件框
	 * 
	 * @param owner
	 *            内容显示区域
	 */
	public Block104(Component owner) {
		super(owner);
		input = new TextBox(this);
		input.setPlaceholder("请输入搜索条件");
		submit = new Button(this);
		submit.setText("搜索");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block104'>");
		html.print("<span>");
		input.output(html);
		submit.output(html);
		html.print("</span>");
		html.println("</div>");
	}

	public TextBox getInput() {
		return input;
	}

	public Button getSubmit() {
		return submit;
	}
}
