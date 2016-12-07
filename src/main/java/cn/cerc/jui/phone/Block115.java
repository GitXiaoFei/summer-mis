package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Span;

/**
 * @author 善贵
 *
 */
public class Block115 extends Component {
	private Span title = new Span();
	private Image image = new Image();

	/**
	 * 分段标题，带一个图标
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block115(Component owner) {
		super(owner);
		title.setText("(title)");
		image.setRole("image");
		image.setSrc("jui/phone/block401_004.png");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block115'>");
		image.output(html);
		title.output(html);
		html.println("</div>");
	}

	public Span getTitle() {
		return title;
	}

	public Image getImage() {
		return image;
	}
}
