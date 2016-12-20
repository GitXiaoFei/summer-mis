package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Span;
import cn.cerc.jpage.vcl.TextBox;

/**
 * 一组左边图标右边文字
 * 
 * @author 郭向军
 *
 */
public class Block123 extends Component {
	private Span title = new Span();
	private UrlRecord urlRecord = new UrlRecord();
	private TextBox textBox = new TextBox();

	public Block123(Component owner) {
		super(owner);
		this.textBox.setMaxlength("20");
		this.textBox.setPlaceholder("请输入");
		this.textBox.setType("text");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block123'>");
		textBox.output(html);
		html.print("<a href='%s'>", this.urlRecord.getUrl());
		this.title.output(html);
		html.print("</a>");
		html.print("</div>");
	}

	public Span getTitle() {
		return title;
	}

	public TextBox getTextBox() {
		return textBox;
	}

	public UrlRecord getUrlRecord() {
		return urlRecord;
	}

}
