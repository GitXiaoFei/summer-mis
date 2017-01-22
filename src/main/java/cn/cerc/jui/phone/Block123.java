package cn.cerc.jui.phone;

import cn.cerc.jpage.core.ActionForm;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Button;
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
	private TextBox textBox = new TextBox();
	private Button button = new Button();
	private ActionForm form = new ActionForm();

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
		this.form.outHead(html);
		this.textBox.output(html);
		this.button.output(html);
		this.form.outFoot(html);
		html.print("</div>");
	}

	public Span getTitle() {
		return title;
	}

	public TextBox getTextBox() {
		return textBox;
	}

	public ActionForm getForm(String id) {
		form.setId(id);
		return form;
	}

	public Button getButton() {
		return button;
	}
}
