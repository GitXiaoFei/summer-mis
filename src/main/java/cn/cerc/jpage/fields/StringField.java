package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.common.HtmlWriter;

public class StringField extends Field {
	// private static final Logger log = Logger.getLogger(Field.class);

	public StringField(DataView owner, String name, String field) {
		super(owner, name, 0);
		this.setField(field);
	}

	public StringField(DataView owner, String name, String field, int width) {
		super(owner, name, width);
		this.setField(field);
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
