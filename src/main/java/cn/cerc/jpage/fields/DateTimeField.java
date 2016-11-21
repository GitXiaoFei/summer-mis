package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.core.HtmlWriter;

public class DateTimeField extends AbstractField {

	public DateTimeField(DataView owner, String name, String field) {
		super(owner, name, field, 10);
	}

	public DateTimeField(DataView owner, String name, String field, int width) {
		super(owner, name, field, width);
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
		return dataSet.getString(getField());
	}
}
