package cn.cerc.jui.phone;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Image;

/**
 * 
 * @author 张弓
 *
 */
public class Block601 extends Component {
	private List<Image> items = new ArrayList<>();

	/**
	 * 多图片显示，左右滑动更换
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block601(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<!-- %s -->", this.getClass().getName());
		html.println("<div class=\"block601\">");
		for (Image button : items)
			button.output(html);
		html.println("</div>");
	}

	public Image addImage(String imgUrl) {
		Image image = new Image();
		image.setSrc(imgUrl);
		items.add(image);
		return image;
	}
}
