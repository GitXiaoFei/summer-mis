package cn.cerc.jpage.core;

import java.util.HashMap;
import java.util.Map;

public class ActionForm extends Component {
	private String action;
	//获取方式
	private String method = "post";
	//集合
	private Map<String, String> items = new HashMap<>();
	private String enctype;

	public ActionForm() {

	}

	public ActionForm(Component owner, String id) {
		super(owner, id);
	}

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
		outHead(html);
		super.output(html);
		outFoot(html);
	}

	/**
	 * 输入头部
	 * 
	 * @param html
	 *            输出器
	 */
	public void outHead(HtmlWriter html) {
		html.print("<form");
		if (this.action != null)
			html.print(" action=\"%s\"", this.action);
		if (this.method != null)
			html.print(" method=\"%s\"", this.method);
		if (this.getId() != null)
			html.print(" id=\"%s\"", this.getId());
		if (this.enctype != null)
			html.print(" enctype=\"%s\"", this.enctype);
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
	}

	/**
	 * 输出尾部
	 * 
	 * @param html
	 *            输出器
	 */
	public void outFoot(HtmlWriter html) {
		html.println("</form>");
	}

	public void addHidden(String key, String value) {
		items.put(key, value);
	}

	public String getEnctype() {
		return enctype;
	}

	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}

	public Map<String, String> getItems() {
		return items;
	}

}
