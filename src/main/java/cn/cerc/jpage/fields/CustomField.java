package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class CustomField extends AbstractField {

	public CustomField(Component dataView, String name, int width) {
		super(dataView, name, width);
	}

	@Override
	public String getText(Record ds) {
		if (buildText == null)
			return "";
		HtmlWriter html = new HtmlWriter();
		buildText.outputText(ds, html);
		return html.toString();
	}

}
