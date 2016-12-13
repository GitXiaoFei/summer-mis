package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class OperaField extends AbstractField {
	private String value = "内容";

	public OperaField(Component owner) {
		this(owner, "操作", 3);
		this.setReadonly(true);
	}

	public OperaField(Component owner, String name, int width) {
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
