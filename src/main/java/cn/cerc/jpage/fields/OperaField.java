package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.common.HtmlWriter;

public class OperaField extends Field {
	private String value = "内容";

	public OperaField(DataView owner) {
		this(owner, "操作", 3);
		this.setReadonly(true);
	}

	public OperaField(DataView owner, String name, int width) {
		super(owner, name, width);
		this.setAlign("center");
		this.setField("_opera_");
		this.setCSSClass_phone("right");
	}

	@Override
	public String getText(Record dataSet) {
		if (buildText != null) {
			HtmlWriter html = new HtmlWriter();
			buildText.outputText(dataSet, html);
			return html.toString();
		}
		return this.value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public OperaField setValue(String value) {
		this.value = value;
		return this;
	}

	@Override
	public OperaField setReadonly(boolean readonly) {
		super.setReadonly(true);
		return this;
	}
}
