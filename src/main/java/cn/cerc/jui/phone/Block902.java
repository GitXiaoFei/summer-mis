package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

/**
 * 用于消息明细显示
 * <p>
 * 标题 + 详细内容
 * 
 * @author HuangRongjun
 *
 */
public class Block902 extends Component {
	private String title = "(title)";
	private String content = "(content)";

	public Block902(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block902'>");

		html.print("<div role='title'>%s</div>", this.title);
		html.print("<div role='content'>%s</div>", this.content);

		html.print("</div>");
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
