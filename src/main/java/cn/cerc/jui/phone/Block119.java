package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Span;

/**
 * 左边图标右边文字
 * 
 * @author 郭向军
 *
 */
public class Block119 extends Component {
	private Span leftTitle = new Span();
	private Image leftImage = new Image();
	private Span rightTitle = new Span();
	private Image rightImage = new Image();
	private UrlRecord leftUrl = new UrlRecord();
	private UrlRecord rightUrl = new UrlRecord();

	public Block119(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block119'>");
		html.print("<ul class='cf'>");
		html.print("<li>");
		html.print("<div class='item'>");
		this.leftImage.output(html);
		this.leftTitle.output(html);
		html.print("</div>");
		html.print("</li>");
		html.print("<li>");
		html.print("<div class='item'>");
		this.rightImage.output(html);
		this.rightTitle.output(html);
		html.print("</div>");
		html.print("</li>");
		html.print("</ul>");
		html.print("</div>");
	}

	public UrlRecord getLeftUrl() {
		return leftUrl;
	}

	public void setLeftUrl(UrlRecord leftUrl) {
		this.leftUrl = leftUrl;
	}

	public UrlRecord getRightUrl() {
		return rightUrl;
	}

	public void setRightUrl(UrlRecord rightUrl) {
		this.rightUrl = rightUrl;
	}

	public Span getRightTitle() {
		return rightTitle;
	}

	public void setRightTitle(String rightTitle) {
		this.rightTitle.setText(rightTitle);
	}

	public void setLeftTitle(String leftTitle) {
		this.leftTitle.setText(leftTitle);
	}

	public Image getLeftImage() {
		return leftImage;
	}

	public void setLeftImage(String leftImage) {
		this.leftImage.setSrc(leftImage);
	}

	public Image getRightImage() {
		return rightImage;
	}

	public void setRightImage(String rightImage) {
		this.rightImage.setSrc(rightImage);
	}
}
