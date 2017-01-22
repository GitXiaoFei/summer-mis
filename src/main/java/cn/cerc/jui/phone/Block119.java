package cn.cerc.jui.phone;

import org.apache.commons.lang.StringUtils;

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
		html.print("<a href='%s'>", this.leftUrl.getUrl());
		this.leftImage.output(html);
		this.leftTitle.output(html);
		html.print("</a>");
		html.print("</div>");
		html.print("</li>");
		if (!StringUtils.isBlank(this.rightImage.getSrc()) && !StringUtils.isBlank(this.rightTitle.getText())) {
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

	public UrlRecord getRightUrl() {
		return rightUrl;
	}

	public Span getRightTitle() {
		return rightTitle;
	}

	public Span getLeftTitle() {
		return leftTitle;
	}

	public Image getLeftImage() {
		return leftImage;
	}

	public Image getRightImage() {
		return rightImage;
	}

}
