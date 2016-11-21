package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.ActionForm;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class GroupBox extends Component {
	private ActionForm form;

	public GroupBox(Component content) {
		super(content);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<div role='group'>");
		if (form != null) {
			html.print(String.format("<form method='%s' action='%s' id='%s'>", form.getMethod(), form.getAction(),
					form.getId()));
			super.output(html);
			html.print("</form>");
		} else {
			super.output(html);
		}
		html.println("</div>");
	}

	public ActionForm getForm() {
		return form;
	}

	public void setForm(ActionForm form) {
		this.form = form;
	}

}
