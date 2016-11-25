package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class GroupBox extends Component {

	public GroupBox(Component content) {
		super(content);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<div role='group'");
		if (getId() != null)
			html.print(" id='%s' ", getId());
		html.print(">");
		super.output(html);
		html.println("</div>");
	}
}
