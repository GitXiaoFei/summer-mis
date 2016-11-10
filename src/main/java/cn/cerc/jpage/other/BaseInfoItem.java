package cn.cerc.jpage.other;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class BaseInfoItem extends Component {
	private String name;
	private String value;

	public BaseInfoItem(Component owner) {
		super(owner);
	}

	public String getName() {
		return name;
	}

	public BaseInfoItem setName(String name) {
		this.name = name;
		return this;
	}

	public Object getValue() {
		return value;
	}

	public BaseInfoItem setValue(String value) {
		this.value = value;
		return this;
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<li>");
		html.print("%s：", this.getName());
		html.print((String) this.value);
		html.print("</li>");
	}
}
