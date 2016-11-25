package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class Image extends Component {
	private String width;
	private String height;
	private String src;
	private String role;
	private String onclick;
	private String alt;

	public Image() {
		super();
	}

	public Image(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<img src='%s'", this.src);
		if (role != null)
			html.print(" role='%s'", this.role);
		if (alt != null)
			html.print(" alt='%s'", this.alt);
		if (width != null)
			html.print(" width='%s'", this.width);
		if (height != null)
			html.print(" height='%s'", this.height);
		if (onclick != null)
			html.print(" onclick='%s'", this.onclick);
		html.println("/>");
	}

	public String getWidth() {
		return width;
	}

	public Image setWidth(String width) {
		this.width = width;
		return this;
	}

	public String getHeight() {
		return height;
	}

	public Image setHeight(String height) {
		this.height = height;
		return this;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}
}
