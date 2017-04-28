package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
/**
 * 
 * LabelBox
 * @author 陈炙宏
 * @time 2017年4月28日下午6:15:02
 *
 */
public class LabelBox extends Component {
	//文本
	private String text;
	//角色
	private String role;
	private String forid;

	public LabelBox() {
		super();
	}

	public LabelBox(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<label");
		if (getId() != null)
			html.print(" id='%s'", this.getId());
		if (role != null)
			html.print(" role='%s'", this.role);
		if (forid != null) {
			html.print(" for='%s'", this.forid);
		}
		html.print(">");
		html.print(text);
		html.println("</label>");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getForid() {
		return forid;
	}

	public void setForid(String forid) {
		this.forid = forid;
	}

}
