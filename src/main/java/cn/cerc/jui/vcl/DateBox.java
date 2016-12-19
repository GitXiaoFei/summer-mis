package cn.cerc.jui.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Label;

@Deprecated
public class DateBox extends Component {
	private String name;
	private String value;
	private String placeholder;
	private Label label;

	public DateBox(Component owner) {
		setOwner(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		if (label != null)
			label.output(html);
		html.print("<input type='text' onClick='WdatePicker()' ");
		html.print(" name='%s'", name);
		html.print(" value='%s'", value == null ? "" : value);
		html.print(" placeholder='%s'", placeholder == null ? "" : value);
		html.print(" />");
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public Label getLabel() {
		if (label == null)
			label = new Label();
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

}
