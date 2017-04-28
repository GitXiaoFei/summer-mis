package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
/**
 * 
 * 按钮
 * @author 陈炙宏
 * @time 2017年4月28日下午6:09:54
 *
 */
public class Button extends Component {
	//名称
	private String name;
	private String value;
	//文本
	private String text;
	//单击事件
	private String onclick;
	//角色
	private String role;

	public Button() {
		super();
	}

	public Button(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<button ");
		if (getId() != null) {
			html.print(String.format(" id=\"%s\"", getId()));
		}
		if (name != null) {
			html.print(String.format(" name=\"%s\"", name));
		}
		if (value != null) {
			html.print(String.format(" value=\"%s\"", value));
		}
		if (role != null) {
			html.print(" role='%s'", this.role);
		}
		if (onclick != null) {
			html.print(String.format(" onclick=\"%s\"", onclick));
		}
		html.print(">");
		html.print(text);
		html.println("</button>");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public void setClickUrl(String url) {
		this.setOnclick(String.format("location.href='%s'", url));
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
