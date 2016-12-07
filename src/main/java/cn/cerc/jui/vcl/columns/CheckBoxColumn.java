package cn.cerc.jui.vcl.columns;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;

/**
 * 值转换列
 * 
 * @author rick_zhou 2016年11月1日 上午11:25:48
 */
public class CheckBoxColumn extends AbstractColumn {
	private String name;

	public CheckBoxColumn(Component owner) {
		super(owner);
	}

	public CheckBoxColumn(Component owner, String name, String field, String title) {
		super(owner, field, title);
		this.name = name;
	}

	public void add(String value) {
		this.getGrid().getCurrent().add(this, value);
	}

	@Override
	public String format(Object value) {
		StringBuffer sb = new StringBuffer();
		sb.append("<td>");
		if (value instanceof Record) {
			Record record = (Record) value;
			sb.append(String.format("<input type='checkbox' id='%s'", this.getField()));
			sb.append(String.format(" name='%s'", this.name));
			if ("true".equals(record.getString(this.getField()))) {
				sb.append(String.format(" checked=true"));
			}
			sb.append(String.format(" data-value='%s'", record.getString(this.getField())));
			sb.append(String.format(">"));

		} else {
			sb.append(value);
		}
		sb.append("</td>");
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
