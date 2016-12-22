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
		rightImage.setSrc("jui/phone/block120_delete.png");
		leftImage.setSrc("jui/phone/block120_edit.png");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block120'>");
		html.print("<ul><li>");
		html.print("<div class='m-left'>");
		this.title.output(html);
		html.print("</div>");
		html.print("<div class='m-right'>");
		html.print("<a href='%s'>", this.getLeftUrl().getUrl());
		this.leftImage.output(html);
		this.leftText.output(html);
		html.print("</a>");
		html.print("<a href='%s'>", this.getRightUrl().getUrl());
		this.rightImage.output(html);
		this.rightText.output(html);
		html.print("</a>");
		html.print("</div>");
		html.print("</div>");
	}

	public Span getTitle() {
		return title;
	}

	public Image getRightImage() {
		return rightImage;
	}

	public Image getLeftImage() {
		return leftImage;
	}

	public Span getLeftText() {
		return leftText;
	}

	public Span getRightText() {
		return rightText;
	}

	public UrlRecord getRightUrl() {
		return rightUrl;
	}

	public UrlRecord getLeftUrl() {
		return leftUrl;
	}

}
