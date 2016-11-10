package cn.cerc.jpage.common;

import java.util.HashMap;
import java.util.Map;

public class ActionForm extends Component {
	private String action;
	private String method = "post";
	private Map<String, String> items = new HashMap<>();

	public ActionForm(String id) {
		super();
		this.setId(id);
	}

	@Deprecated
	public ActionForm(String id, String action) {
		this.setId(id);
		this.setAction(action);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<form");
		if (this.action != null)
			html.print(" action=\"%s\"", this.action);
		if (this.method != null)
			html.print(" method=\"%s\"", this.method);
		if (this.getId() != null)
			html.print(" id=\"%s\"", this.getId());
		html.println(">");
		for (String key : items.keySet()) {
			String value = items.get(key);
			html.print("<input");
			html.print(" type=\"hidden\"");
			html.print(" name=\"%s\"", key);
			html.print(" id=\"%s\"", key);
			html.print(" value=\"%s\"", value);
			html.println("/>");
		}
		super.output(html);
		html.println("</form>");
	}

	public void addHidden(String key, String value) {
		items.put(key, value);
	}
}
