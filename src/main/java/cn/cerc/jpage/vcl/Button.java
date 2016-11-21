package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class Button extends Component {
	private String text;

	public Button(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<button>");
		html.print(text);
		html.print("</button>");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
