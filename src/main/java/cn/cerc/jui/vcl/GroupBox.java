package cn.cerc.jui.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class GroupBox extends Component {
	private String name;
	private boolean visible = false;

	public GroupBox(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<div class='group-box' name='%s' id='%s' visible='%s'>", name, this.getId(), visible);
		super.output(html);
		html.print("</div>");
	}

}
