package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Span;

/**
 * 标题
 * 
 * @author 郭向军
 *
 */
public class Block121 extends Component {
	private Span title = new Span();
	private Image leftImage = null;
	private UrlRecord leftUrl = new UrlRecord();
	private UrlRecord rightUrl = new UrlRecord();
	private Span rightText = null;

	public Block121(Component owner) {
		super(owner);
	}
	
	public Block121() {
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<header  class='block121'>");
		if (this.leftImage != null) {
			html.print("<a href='%s'  class='arrow-left'>", this.leftUrl.getUrl());
			this.leftImage.output(html);
			html.print("</a>");
		}
		html.print("<h1 class='title'>");
		this.title.output(html);
		html.print("</h1>");
		if (this.rightText != null) {
			html.print("<a href='%s' class='arrow-right'>", this.rightUrl.getUrl());
			this.rightText.output(html);
			html.print("</a>");
		}
		html.print("</header>");
	}

	public UrlRecord getRightUrl() {
		return rightUrl;
	}

	public void setRightUrl(UrlRecord rightUrl) {
		this.rightUrl = rightUrl;
	}

	public Span getRightText() {
		return rightText;
	}

	public void setRightText(String rightText) {
		this.rightText = new Span();
		this.rightText.setText(rightText);
	}

	public Span getTitle() {
		return title;
	}

	public void setTitle(String text) {
		this.title.setText(text);
	}

	public void setLeftImage(String leftImage) {
		this.leftImage = new Image();
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
