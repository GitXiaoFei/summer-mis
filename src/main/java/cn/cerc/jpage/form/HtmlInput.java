package cn.cerc.jpage.form;

import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.common.HtmlWriter;

public class HtmlInput extends Component {
	private String type;
	private String name;

	public HtmlInput() {

	}

	public HtmlInput(Component owner) {
		super(owner);
	}

	public void output(HtmlWriter html) {
		html.print("<input");
		if (this.type != null)
			html.print(" type='%s'", this.type);
		if (this.name != null)
			html.print(" name='%s'", this.getName());
		else
			html.print(" name='%s'", this.getId());
		html.print(" id='%s'", this.getId());
		html.print("/>");
	}

	public String getType() {
		return type;
	}

	public HtmlInput setType(String type) {
		this.type = type;
		return this;
	}

	public String getName() {
		return name;
	}

	public HtmlInput setName(String name) {
		this.name = name;
		return this;
	}
}
