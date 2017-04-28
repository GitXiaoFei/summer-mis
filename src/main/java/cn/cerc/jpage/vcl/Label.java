package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
/**
 * 
 * Label标签
 * @author 陈炙宏
 * @time 2017年4月28日下午6:14:40
 *
 */
public class Label extends Component {
	private String caption;
	//链接
	private String url;
	//集中对象
	private String focusTarget;

	public String getFocusTarget() {
		return focusTarget;
	}

	public void setFocusTarget(String focusTarget) {
		this.focusTarget = focusTarget;
	}

	public Label(Component component) {
		super(component);
	}

	public Label() {
		super();
	}

	@Override
	public void output(HtmlWriter html) {
		if (url == null) {
			html.print("<label");
			if (focusTarget != null)
				html.print(" for='%s'", focusTarget);
			html.print(">%s</label>", this.caption);
		} else
			html.print("<a href='%s'>%s</a>", this.url, this.caption);
	}

	public Label(String caption, String url) {
		super();
		this.caption = caption;
		this.url = url;
	}

	public Label(String caption) {
		super();
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
