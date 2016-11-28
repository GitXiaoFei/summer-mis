package cn.cerc.jpage.fields;

import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.common.HtmlWriter;

public class ButtonField extends Field {
	private String data;

	public ButtonField() {
		super(null, null, 0);
	}

	public ButtonField(DataView owner, String name, String id, String data) {
		super(owner, name, 0);
		this.setField(id);
		this.data = data;
		this.setId(id);
	}

	public String getData() {
		return data;
	}

	public ButtonField setData(String data) {
		this.data = data;
		return this;
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<button name=\"%s\"", this.getId());
		if (this.data != null)
			html.print(" value=\"%s\"", this.data);
		html.print(">");
		html.print("%s</button>", this.getName());
	}
}
