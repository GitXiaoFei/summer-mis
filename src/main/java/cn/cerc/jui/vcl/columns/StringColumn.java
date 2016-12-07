package cn.cerc.jui.vcl.columns;

import cn.cerc.jpage.core.Component;

public class StringColumn extends AbstractColumn {

	public StringColumn(Component owner) {
		super(owner);
	}

	public StringColumn(Component owner, String field, String title) {
		super(owner, field, title);
	}

	public void add(String value) {
		this.getGrid().getCurrent().add(this, value);
	}
}
