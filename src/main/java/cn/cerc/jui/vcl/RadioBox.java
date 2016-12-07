package cn.cerc.jui.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class RadioBox extends Component {
	private String name = "";
	private String value = "";
	private boolean isSelected = false;
	private Label label;

	@Override
	public void output(HtmlWriter html) {
		if (label != null)
			label.output(html);
		html.print("<input type='radio' ");
		if (isSelected)
			html.print(" checked=checked");
		html.print(" name='%s' value='%s'>", name, value);
	}

	public RadioBox(Component owner) {
		super(owner);
	}

	public RadioBox() {
		super();
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

	public Label getLabel() {
		if (label == null)
			label = new Label();
		return label;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

}
