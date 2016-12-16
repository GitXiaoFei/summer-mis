package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Span;

/**
 * 提示块
 * 
 * @author 郭向军
 *
 */
public class Block126 extends Component {
	private Span title = new Span();

	public Block126(Component owner) {
		super(owner);
		title.setText("部门一");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block126'>");
		this.title.output(html);
		html.print("</div>");
	}

	public Span getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

}
