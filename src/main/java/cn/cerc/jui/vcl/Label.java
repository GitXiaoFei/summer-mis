package cn.cerc.jui.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class Label extends Component {
	private String caption;
	private String url;
	private String for_;

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
			if (for_ != null)
				html.print(" for='%s'", for_);
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

	public String getFor_() {
		return for_;
	}

	public void setFor_(String for_) {
		this.for_ = for_;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
