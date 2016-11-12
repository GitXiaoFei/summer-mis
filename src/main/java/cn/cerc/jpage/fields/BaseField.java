package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.core.HtmlWriter;

public class BaseField extends Field {

	public BaseField(DataView dataView, String name, int width) {
		super(dataView, name, width);
	}

	@Override
	public String getText(Record ds) {
		if (buildText == null)
			return null;
		HtmlWriter html = new HtmlWriter();
		buildText.outputText(ds, html);
		return html.toString();
	}

}
