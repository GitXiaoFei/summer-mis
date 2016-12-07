package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Button;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Label;

/**
 * @author 善贵
 *
 */
public class Block116 extends Component {
	private Label title = new Label();
	private Image image = new Image();
	private Button button = new Button();

	/**
	 * 分段标题，带一个图标及按钮
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block116(Component owner) {
		super(owner);
		title.setText("(title)");
		image.setRole("image");
		image.setSrc("jui/phone/block401_003.png");
		button.setText("(button)");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block116'>");
		image.output(html);
		title.output(html);
		button.output(html);
		html.println("</div>");
	}

	public Label getTitle() {
		return title;
	}

	public Image getImage() {
		return image;
	}

	public Button getButton() {
		return button;
	}
}
