package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class Button extends Component {
	private String name;
	private String value;
	private String text;

	public Button() {
		super();
	}

	public Button(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<button");
		if (getId() != null) {
			html.print(String.format(" id=\"%s\"", getId()));
		}
		if (name != null) {
			html.print(String.format(" name=\"%s\"", name));
		}
		if (value != null) {
			html.print(String.format(" value=\"%s\"", value));
		}
		html.print(">");
		html.print(text);
		html.print("</button>");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
