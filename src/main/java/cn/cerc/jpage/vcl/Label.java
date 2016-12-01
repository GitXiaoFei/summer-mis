package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class Label extends Component {
	private String text;
	private String role;

	public Label() {
		super();
	}

	public Label(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<span");
		if (getId() != null)
			html.print(" id='%s'", this.getId());
		if (role != null)
			html.print(" role='%s'", this.role);
		html.print(">");
		html.print(text);
		html.println("</span>");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
