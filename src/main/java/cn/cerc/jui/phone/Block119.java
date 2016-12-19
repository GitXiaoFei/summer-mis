package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Span;

/**
 * 两组左边图标右边文字
 * 
 * @author 郭向军
 *
 */
public class Block119 extends Component {
	private Span leftTitle = null;
	private Image leftImage = null;
	private Span rightTitle = null;
	private Image rightImage = null;
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
		html.print("<a href='%s'>", this.leftUrl.getUrl());
		this.leftImage.output(html);
		this.leftTitle.output(html);
		html.print("</a>");
		html.print("</div>");
		html.print("</li>");
		if (this.rightImage != null && this.rightTitle != null) {
			html.print("<li>");
			html.print("<div class='item'>");
			html.print("<a href='%s'>", this.rightUrl.getUrl());
			this.rightImage.output(html);
			this.rightTitle.output(html);
			html.print("</a>");
			html.print("</div>");
			html.print("</li>");
		}
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
		this.rightTitle = new Span();
		this.rightTitle.setText(rightTitle);
	}

	public void setLeftTitle(String leftTitle) {
		this.leftTitle = new Span();
		this.leftTitle.setText(leftTitle);
	}

	public Image getLeftImage() {
		return leftImage;
	}

	public void setLeftImage(String leftImage) {
		this.leftImage = new Image();
		this.leftImage.setSrc(leftImage);
	}

	public Image getRightImage() {
		return rightImage;
	}

	public void setRightImage(String rightImage) {
		this.rightImage = new Image();
		this.rightImage.setSrc(rightImage);
	}
}
