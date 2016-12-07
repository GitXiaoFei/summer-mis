package cn.cerc.jui.vcl.columns;

import cn.cerc.jpage.core.Component;

public class MoneyColumn extends AbstractColumn {

	public MoneyColumn(Component owner) {
		super(owner);
	}

	@Override
	public String format(Object value) {
		return "$" + value.toString();
	}

	public void add(double value) {
		this.getGrid().getCurrent().add(this, "" + value);
	}
}
