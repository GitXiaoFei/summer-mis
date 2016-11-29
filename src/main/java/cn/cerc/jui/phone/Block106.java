package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Label;

public class Block106 extends Component {
	private Label content = new Label();

	/**
	 * 简单显示文字类信息，仅用于显示，不可修改
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block106(Component owner) {
		super(owner);
		content.setText("(content)");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block106' role='row'>");
		content.output(html);
		html.println("</div>");
	}

	public Label getContent() {
		return content;
	}

}
