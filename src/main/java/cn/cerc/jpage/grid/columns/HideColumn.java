package cn.cerc.jpage.grid.columns;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;

public class HideColumn extends AbstractColumn {

	public HideColumn(Component owner) {
		super(owner);
	}

	public HideColumn(Component owner, String field, String title) {
		super(owner, field, title);
	}

	public void add(String value) {
		this.getGrid().getCurrent().addData(this, value);
	}

	@Override
	public String format(Object value) {
		StringBuffer sb = new StringBuffer();
		sb.append("<td class='hide'>");
		if (value instanceof Record) {
			sb.append(super.format(value));
		} else {
			sb.append(value);
		}
		sb.append("</td>");
		return sb.toString();
	}

}
