package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Button;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Label;

/**
 * 
 * @author 张弓
 *
 */
public class Block991 extends Component {
	private Image image = new Image();
	private Button button = new Button(this);
	private Label remark = new Label(this);

	/**
	 * 底部状态栏：1个功能按钮+提示文字
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block991(Component owner) {
		super(owner);
		image.setSrc("jui/phone/block991_back.png");
		button.setText("(button)");
		remark.setText("(remark)");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.println("<div class=\"block991\">");
		image.output(html);
		button.output(html);
		remark.output(html);
		html.println("</div>");
	}

	public Label getRemark() {
		return remark;
	}

	public Button getButton() {
		return button;
	}

	public Image getImage() {
		return image;
	}
}
