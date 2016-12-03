package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

/**
 * 
 * 单选框
 * 
 * @author 张弓
 *
 */
public class CheckBox extends Component {
	private String name;
	private String type;
	private String value;
	private String role;
	private boolean checked;

	public CheckBox() {
		super();
		type = "checkbox";
		checked = false;
	}

	public CheckBox(Component owner) {
		super(owner);
		type = "checkbox";
		checked = false;
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<input");
		if (this.getId() != null)
			html.print(" id='%s'", this.getId());
		if (this.name != null)
			html.print(" name='%s'", this.getName());
		if (type != null)
			html.print(" type=\"%s\"", type);
		if (value != null)
			html.print(" value='%s'", this.value);
		if (role != null)
			html.print(" role='%s'", this.role);
		if (checked)
			html.print(" checked='checked'");
		html.println("/>");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
