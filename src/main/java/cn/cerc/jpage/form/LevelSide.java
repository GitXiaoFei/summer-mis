package cn.cerc.jpage.form;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class LevelSide extends Component {
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// category
	@Override
	public void output(HtmlWriter html) {
		if (this.getComponents().size() == 0)
			return;
		html.print("<div style=\"margin:0.5em 1em 1em 1em;\" id=\"%s\">", this.getId());
		html.println(this.title);
		for (Component component : this.getComponents())
			component.output(html);
		html.print("</div>");
	}
}
