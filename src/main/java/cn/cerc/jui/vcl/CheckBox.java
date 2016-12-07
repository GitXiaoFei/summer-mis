package cn.cerc.jui.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

/**
 * 复选框
 * 
 * @author rick_zhou 2016年11月9日 下午4:06:41
 */
public class CheckBox extends Component {
	private Label label;
	private String name = "";
	private String value = "";
	private boolean isChecked = false;

	@Override
	public void output(HtmlWriter html) {
		if (label != null) {
			label.setFor_(this.getId());
			label.output(html);
		}

		html.print("<input type='checkbox'");
		if (this.getId() != null)
			html.print(" id='%s'", this.getId());
		html.print(" name='%s' value='%s' ", name, value);
		if (isChecked)
			html.print(" checked=checked");
		html.print(" />");
	}

	public CheckBox() {
		super();
	}

	public CheckBox(Component owner) {
		super(owner);
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

	public boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Label getLabel() {
		if (label == null)
			label = new Label();
		return label;
	}
}
