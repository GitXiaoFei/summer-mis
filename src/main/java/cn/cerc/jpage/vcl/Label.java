package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class Label extends Component {
	private String text;

	@Override
	public void output(HtmlWriter html) {
		html.print("<span>");
		html.print(text);
		html.print("</span>");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
