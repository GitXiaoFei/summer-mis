package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Span;

/**
 * 部门管理
 * @author rascal.guo
 *
 */
public class Block120 extends Component {
	private Span title = new Span();
	private Image delImage = new Image();
	private Image editImage = new Image();
	private Span delText = new Span();
	private Span editText = new Span();
	private UrlRecord delUrl = new UrlRecord();
	private UrlRecord editUrl = new UrlRecord();

	public Block120(Component owner) {
		super(owner);
		editText.setText("修改");
		delText.setText("删除");
	}



	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block120'>");
		html.print("<div>");
		this.title.output(html);
		html.print("</div>");
		html.print("<div><div>");
		this.editImage.output(html);
		html.print("&nbsp;");
		this.getEditText().output(html);
		html.print("<div>");
		this.delImage.output(html);
		html.print("&nbsp;");
		this.delText.output(html);
		html.print("</div>");
		html.print("</div>");
	}



	public Span getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title.setText(title);
	}



	public Image getDelImage() {
		return delImage;
	}



	public void setDelImage(String delImage) {
		this.delImage.setSrc(delImage);
	}



	public Image getEditImage() {
		return editImage;
	}



	public void setEditImage(String editImage) {
		this.editImage.setSrc(editImage);
	}



	public Span getDelText() {
		return delText;
	}



	public void setDelText(String delText) {
		this.delText.setText(delText);
	}



	public Span getEditText() {
		return editText;
	}



	public void setEditText(String editText) {
		this.editText.setText(editText);
	}



	public UrlRecord getDelUrl() {
		return delUrl;
	}



	public void setDelUrl(UrlRecord delUrl) {
		this.delUrl = delUrl;
	}



	public UrlRecord getEditUrl() {
		return editUrl;
	}



	public void setEditUrl(UrlRecord editUrl) {
		this.editUrl = editUrl;
	}


}
