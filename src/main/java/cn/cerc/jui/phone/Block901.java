package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class Block901 extends Component {
	private String message;
	private String help = "(help)";

	/**
	 * 带图标的多行内容显示，如采购成功确认讯息显示
	 * 
	 * @param content
	 *            所在显示区域
	 */
	public Block901(Component content) {
		super(content);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block901'>");
		if (message != null) {
			html.print(getMessage());
		}
		html.println("</div>");

		html.print("<div>");
		html.print(getHelp());
		html.println("</div>");
	}
}
