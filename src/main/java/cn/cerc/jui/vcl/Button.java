package cn.cerc.jui.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jui.vcl.enums.ButtonEnum;

public class Button extends Component {
	private String name;
	private String value;
	private String url;
	private String checkBoxName;
	private ButtonEnum type = ButtonEnum.button;

	@Override
	public void output(HtmlWriter html) {
		if (this.checkBoxName != null) {// js获取复选框
			html.print("<input type='button' name='%s' value='%s' onclick=\"fnCheckLinkBtn('%s','%s')\" />", name,
					value, checkBoxName, url);
			return;
		}
		if (this.url != null) {// 超链接跳转
			html.print("<input type='button' name='%s' value='%s' onclick=\"javascript:window.location.href='%s'\" />",
					name, value, url);
			return;
		}
		if (type.equals(ButtonEnum.submit)) {// 表单提交
			html.print("<input type='submit' name='%s' value='%s' />", name, value);
			return;
		}
		html.print("<input type='button' name='%s' value='%s' />", name, value);
	}

	public Button(Component component) {
		super(component);
	}

	public Button() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCheckBoxName() {
		return checkBoxName;
	}

	public void setCheckBoxName(String checkBoxName) {
		this.checkBoxName = checkBoxName;
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

	public ButtonEnum getType() {
		return type;
	}

	public void setType(ButtonEnum type) {
		this.type = type;
	}

}
