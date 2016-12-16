package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Span;

/**
 * 标题
 * @author 郭向军
 *
 */
public class Block121 extends Component {
	private Span title = new Span();
	private Image leftImage = new Image();
	private UrlRecord leftUrl = new UrlRecord();

	public Block121(Component owner) {
		super(owner);
		leftImage.setSrc("/img/icon/icon_arrow_left.png");
		leftUrl.setSite("javascript:window.location.go(-1)");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<header  class='block121'>");
		html.print("<a href='%s'  class='arrow-left'>", this.leftUrl.getUrl());
		this.leftImage.output(html);
		html.print("</a>");
		html.print("<h1 class='title'>");
		this.title.output(html);
		html.print("</h1>");
		html.print("</header>");
	}

	public Span getTitle() {
		return title;
	}

	public void setTitle(String text) {
		this.title.setText(text);
	}

	public void setLeftImage(String leftImage) {
		this.leftImage.setSrc(leftImage);
	}

	public Image getLeftImage() {
		return this.leftImage;
	}

	public UrlRecord getLeftUrl() {
		return leftUrl;
	}

	public void setLeftUrl(UrlRecord leftUrl) {
		this.leftUrl = leftUrl;
	}

}
