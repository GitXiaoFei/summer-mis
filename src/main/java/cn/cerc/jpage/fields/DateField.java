package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.form.Title;

public class DateField extends Field {

	public DateField(DataView owner, String name, String field) {
		super(owner, name, field, 5);
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
