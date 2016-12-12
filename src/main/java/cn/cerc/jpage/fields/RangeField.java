package cn.cerc.jpage.fields;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jui.vcl.columns.IColumn;

public class RangeField extends AbstractField implements DataView {

	public RangeField(DataView dataView, String name) {
		super(dataView, name, 0);
	}

	@Override
	public String getText(Record rs) {
		return getDefaultText(rs);
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
			AbstractField child = null;
			for (Component component : this.getComponents()) {
				if (component instanceof AbstractField) {
					if (child != null)
						html.print("-");
					child = (AbstractField) component;
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
	public void addField(IColumn field) {
		if (field instanceof Component)
			this.addComponent((Component) field);
		else
			throw new RuntimeException("不支持的数据类型：" + field.getClass().getName());
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
		AbstractField child = null;
		for (Component component : this.getComponents()) {
			if (component instanceof AbstractField) {
				child = (AbstractField) component;
				child.updateField();
			}
		}
	}
}
