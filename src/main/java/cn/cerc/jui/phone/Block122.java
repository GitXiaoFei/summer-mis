package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Span;

/**
 * 一组左边图标右边文字
 * 
 * @author 郭向军
 *
 */
public class Block122 extends Component {
	private Span title = new Span();
	private Image image = new Image();
	private UrlRecord urlRecord = new UrlRecord();

	public Block122(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block122'>");
		html.print("<a href='%s'>", this.urlRecord.getUrl());
		html.print("<div class='item'>");
		this.image.output(html);
		this.title.output(html);
		html.print("</div>");
		html.print("</div>");
	}

	public Span getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image.setSrc(image);
	}

	public UrlRecord getUrlRecord() {
		return urlRecord;
	}

	public void setUrlRecord(UrlRecord urlRecord) {
		this.urlRecord = urlRecord;
	}

}
