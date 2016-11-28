package cn.cerc.jpage.fields;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.common.HtmlWriter;

public class OptionField extends StringField {
	private String defaultValue;
	private int size;// 默认显示行数
	private Map<String, String> items = new LinkedHashMap<>();

	public OptionField(DataView owner, String name, String field) {
		super(owner, name, field, 0);
	}

	public OptionField(DataView owner, String name, String field, int width) {
		super(owner, name, field, width);
	}

	public OptionField add(String key, String text) {
		if (this.defaultValue == null)
			defaultValue = key;
		items.put(key, text);
		return this;
	}

	@Override
	public String getString() {
		String result = super.getString();
		if (result == null)
			return this.defaultValue;
		return result;
	}

	@Override
	public void output(HtmlWriter html) {
		Record dataSet = dataView != null ? dataView.getRecord() : null;
		String current = this.getText(dataSet);
		html.println("<label for=\"%s\">%s</label>", this.getId(), this.getName() + "：");
		if (size > 0) {
			html.println("<select id=\"%s\" name=\"%s\" size=\"%s\">", this.getId(), this.getId(), this.getSize());
		} else {
			html.println("<select id=\"%s\" name=\"%s\">", this.getId(), this.getId());
		}
		for (String key : items.keySet()) {
			String value = items.get(key);
			html.print("<option value=\"%s\"", key);
			if (key.equals(current))
				html.print(" selected");
			html.print(">");
			html.println(String.format("%s</option>", value));
		}
		html.println("</select>");
		html.print("<span></span>");
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}