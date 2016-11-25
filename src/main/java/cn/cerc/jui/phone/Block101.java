package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Label;

public class Block101 extends Component {
	private Label title = new Label();
	private Image image = new Image();
	private UrlRecord url = new UrlRecord();

	/**
	 * 上游在线订货单手机版页面
	 * 
	 * @param owner
	 *            所在内容显示区
	 */
	public Block101(Component owner) {
		super(owner);
		title.setText("(title)");
		image.setSrc("jui/phone/block101-go.png");
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block101' role='row'>");
		html.print("<div role='title'>");
		title.output(html);
		html.print("</div>");
		html.print("<a href='%s'>", url.getUrl());
		image.output(html);
		html.println("</a>");
		html.println("</div>");
	}

	public Label getTitle() {
		return title;
	}

	public Image getImage() {
		return image;
	}

	public UrlRecord getUrl() {
		return url;
	}
}
