package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Span;

/**
 * 部门管理
 * 
 * @author 郭向军
 *
 */
public class Block120 extends Component {
	private Span title = new Span();
	private Image rightImage = new Image();
	private Image leftImage = new Image();
	private Span leftText = new Span();
	private Span rightText = new Span();
	private UrlRecord rightUrl = new UrlRecord();
	private UrlRecord leftUrl = new UrlRecord();

	public Block120(Component owner) {
		super(owner);
		title.setText("item");
		leftText.setText("修改");
		rightText.setText("删除");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block120'>");
		html.print("<div>");
		this.title.output(html);
		html.print("</div>");
		html.print("<div><div>");
		this.leftImage.output(html);
		html.print("&nbsp;");
		html.print("<a href='%s'>",this.getLeftUrl().getUrl());
		this.leftText.output(html);
		html.print("</a>");
		html.print("<div>");
		this.rightImage.output(html);
		html.print("&nbsp;");
		html.print("<a href='%s'>",this.getRightUrl().getUrl());
		this.rightText.output(html);
		html.print("</a>");
		html.print("</div>");
		html.print("</div>");
	}

	public Span getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public Image getRightImage() {
		return rightImage;
	}

	public void setRightImage(Image rightImage) {
		this.rightImage = rightImage;
	}

	public Image getLeftImage() {
		return leftImage;
	}

	public void setLeftImage(Image leftImage) {
		this.leftImage = leftImage;
	}

	public Span getLeftText() {
		return leftText;
	}

	public void setLeftText(String leftText) {
		this.leftText.setText(leftText);
	}

	public Span getRightText() {
		return rightText;
	}

	public void setRightText(String rightText) {
		this.rightText.setText(rightText);
	}

	public UrlRecord getRightUrl() {
		return rightUrl;
	}

	public void setRightUrl(UrlRecord rightUrl) {
		this.rightUrl = rightUrl;
	}

	public UrlRecord getLeftUrl() {
		return leftUrl;
	}

	public void setLeftUrl(UrlRecord leftUrl) {
		this.leftUrl = leftUrl;
	}

}
