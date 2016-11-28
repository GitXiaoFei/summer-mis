package cn.cerc.jpage.fields;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.common.HtmlWriter;

public class RangeField extends Field implements DataView {

	public RangeField(DataView dataView, String name) {
		super(dataView, name, 0);
	}

	@Override
	public void output(HtmlWriter html) {
		Record dataSet = dataView != null ? dataView.getRecord() : null;
		if (this.hidden) {
			html.print("<input");
			html.print(" type=\"hidden\"");
			html.print(" name=\"%s\"", this.getId());
			html.print(" id=\"%s\"", this.getId());
			String value = this.getText(dataSet);
			if (value != null)
				html.print(" value=\"%s\"", value);
			html.println("/>");
		} else {
			html.println("<label for=\"%s\">%s</label>", this.getId(), this.getName() + "：");
			Field child = null;
			for (Component component : this.getComponents()) {
				if (component instanceof Field) {
					if (child != null)
						html.print("-");
					child = (Field) component;
					String val = child.getCSSClass_phone();
					child.setCSSClass_phone("price");
					child.outputInput(html, dataSet);
					child.setCSSClass_phone(val);
				}
			}
			if (this.dialog != null) {
				html.print("<span>");
				html.print("<a href=\"javascript:%s('%s')\">", this.dialog, this.getId());
				html.print("<img src=\"images/select-pic.png\">");
				html.print("</a>");
				html.print("</span>");
			} else {
				html.print("<span></span>");
			}
		}
	}

	@Override
	public void addField(Field field) {
		this.addComponent(field);
	}

	@Override
	public Record getRecord() {
		return dataView.getRecord();
	}

	@Override
	public boolean isReadonly() {
		return dataView.isReadonly();
	}

	@Override
	public HttpServletRequest getRequest() {
		return dataView.getRequest();
	}

	@Override
	public void updateField() {
		Field child = null;
		for (Component component : this.getComponents()) {
			if (component instanceof Field) {
				child = (Field) component;
				child.updateField();
			}
		}
	}
}
