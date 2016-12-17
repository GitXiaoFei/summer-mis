package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.form.Title;

public class DateField extends AbstractField {

	public DateField(Component owner, String name, String field) {
		super(owner, name, 5);
		this.setField(field);
		this.setDialog("showDateDialog");
	}

	@Override
	public Title createTitle() {
		Title title = super.createTitle();
		title.setType("date");
		return title;
	}

	@Override
	public String getText(Record dataSet) {
		if (dataSet == null)
			return null;
		if (buildText != null) {
			HtmlWriter html = new HtmlWriter();
			buildText.outputText(dataSet, html);
			return html.toString();
		}
		if (dataSet.hasValue(getField()))
			return dataSet.getDate(getField()).getDate();
		else
			return "";
	}
}
