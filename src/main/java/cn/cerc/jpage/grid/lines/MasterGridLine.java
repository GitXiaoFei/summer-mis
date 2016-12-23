package cn.cerc.jpage.grid.lines;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jpage.core.DataSource;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.IColumn;
import cn.cerc.jpage.core.IField;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.grid.RowCell;

public class MasterGridLine extends AbstractGridLine {
	private String primaryKey;

	public MasterGridLine(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void output(HtmlWriter html, int lineNo) {
		DataSet dataSet = dataSource.getDataSet();
		html.print("<tr");
		html.print(" id='%s'", "tr" + dataSet.getRecNo());
		if (this.getPrimaryKey() != null)
			html.print(" data-rowid='%s'", dataSet.getString(this.getPrimaryKey()));
		html.println(">");
		for (RowCell item : this.getCells()) {
			IField obj = item.getFields().get(0);
			html.print("<td");
			if (item.getColSpan() > 1)
				html.print(" colspan=\"%d\"", item.getColSpan());
			if (item.getStyle() != null)
				html.print(" style=\"%s\"", item.getStyle());
			else if (obj.getWidth() == 0)
				html.print(" style=\"%s\"", "display:none");

			if (item.getAlign() != null)
				html.print(" align=\"%s\"", item.getAlign());
			else if (obj.getAlign() != null)
				html.print(" align=\"%s\"", obj.getAlign());

			if (item.getRole() != null)
				html.print(" role=\"%s\"", item.getRole());
			else if (obj.getField() != null)
				html.print(" role=\"%s\"", obj.getField());

			html.println(">");
			if (obj instanceof AbstractField) {
				AbstractField field = (AbstractField) obj;
				if (field instanceof IColumn)
					html.print(((IColumn) field).format(dataSource.getDataSet().getCurrent()));
				else if (field instanceof AbstractField)
					outputField(html, (AbstractField) field);
				else
					throw new RuntimeException("暂不支持的数据类型：" + field.getClass().getName());
			}
			html.println("</td>");
		}
		html.println("</tr>");
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public void addField(IField field) {
		getFields().add(field);
		RowCell col;
		col = new RowCell();
		col.setAlign(field.getAlign());
		col.setRole(field.getField());
		getCells().add(col);
		col.addField(field);
	}

	@Override
	public boolean isReadonly() {
		return dataSource.isReadonly();
	}

	@Override
	public void updateValue(String id, String code) {
		dataSource.updateValue(id, code);
	}

}
