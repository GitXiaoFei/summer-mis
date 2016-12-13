package cn.cerc.jui.vcl.columns;

import cn.cerc.jpage.core.Component;

public class IndexColumn extends AbstractColumn {

	public IndexColumn(Component owner) {
		super(owner);
	}

	public IndexColumn(Component owner, String title) {
		super(owner, title);
	}

	public void add(String value) {
		this.getGrid().getCurrent().addData(this, value);
	}
}
