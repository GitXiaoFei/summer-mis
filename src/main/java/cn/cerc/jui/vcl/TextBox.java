package cn.cerc.jui.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jui.vcl.enums.TextEnum;

public class TextBox extends Component {
	private String name = "";
	private String value = "";
	private String placeholder = "";
	private TextEnum type = TextEnum.text;
	private Label label;
	private String _class = "";// 给元素添加Class
	private String readonly = "";// 是否只读
	private String required = "";// 输入验证验证

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	@Override
	public void output(HtmlWriter html) {
		if (label != null)
			label.output(html);
		if (type.equals(TextEnum.multi)) {
			html.print("<textarea class='%s' %s %s name='%s' placeholder='%s' >%s</textarea>", _class, readonly,
					required, name, placeholder, value);
		} else {
			html.print("<input class='%s' %s %s type='%s' name='%s' placeholder='%s' value='%s' />", _class, readonly,
					required, type, name, placeholder, value);
		}

	}

	public TextBox(Component owner) {
		super(owner);
	}

	public TextBox() {
		super();
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(Boolean flag) {
		if (flag)
			this.readonly = "readonly";
	}

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
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

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public Label getLabel() {
		if (label == null)
			label = new Label();
		return label;
	}

	public TextEnum getType() {
		return type;
	}

	public void setType(TextEnum type) {
		this.type = type;
	}

}
