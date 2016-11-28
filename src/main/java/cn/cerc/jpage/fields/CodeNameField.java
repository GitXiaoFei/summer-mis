package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.common.HtmlWriter;

public class CodeNameField extends StringField {
	private String nameField;

	public CodeNameField(DataView owner, String name, String field) {
		super(owner, name, field, 0);
	}

	@Override
	public void updateField() {
		if (dataView != null) {
			dataView.updateValue(this.getId(), this.getField());
			dataView.updateValue(getNameField(), getNameField());
		}
	}

	@Override
	public void output(HtmlWriter html) {
		Record dataSet = dataView != null ? dataView.getRecord() : null;
		if (this.isHidden()) {
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

			html.print("<input");
			html.print(" type=\"hidden\"");
			html.print(" name=\"%s\"", this.getId());
			html.print(" id=\"%s\"", this.getId());
			String codeValue = this.getText(dataSet);
			if (codeValue != null)
				html.print(" value=\"%s\"", codeValue);
			html.println("/>");

			html.print("<input");
			html.print(" type=\"text\"");
			html.print(" name=\"%s\"", getNameField());
			html.print(" id=\"%s\"", getNameField());
			String nameValue = null;
			if (dataSet != null) {
				nameValue = dataSet.getString(getNameField());
				if (nameValue != null)
					html.print(" value=\"%s\"", nameValue);
			}
			if (this.isReadonly())
				html.print(" readonly=\"readonly\"");
			if (this.isAutofocus())
				html.print(" autofocus");
			if (this.isRequired())
				html.print(" required");
			if (this.getPlaceholder() != null)
				html.print(" placeholder=\"%s\"", this.getPlaceholder());
			html.println("/>");

			html.print("<span>");
			if (this.getDialog() != null) {
				html.print("<a href=\"javascript:%s('%s,%s')\">", this.getDialog(), this.getId(), getNameField());
				html.print("<img src=\"images/select-pic.png\">");
				html.print("</a>");
			}
			html.print("</span>");
		}
	}

	public String getNameField() {
		if (nameField != null)
			return nameField;
		return this.getField() + "_name";
	}

	public CodeNameField setNameField(String nameField) {
		this.nameField = nameField;
		return this;
	}
}
