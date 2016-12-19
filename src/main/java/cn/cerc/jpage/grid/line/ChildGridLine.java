package cn.cerc.jpage.grid.line;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jpage.core.DataSource;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.IColumn;
import cn.cerc.jpage.core.IField;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.grid.RowCell;

public class ChildGridLine extends AbstractGridLine {

	public ChildGridLine(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void output(HtmlWriter html, DataSet dataSet, int lineNo) {
		html.print("<tr");
		html.print(" id='%s_%s'", "tr" + dataSet.getRecNo(), lineNo);
		html.println(">");
		for (RowCell item : this.getCells()) {
			html.print("<td");
			if (item.getColSpan() > 1)
				html.print(" colspan=\"%d\"", item.getColSpan());
			if (item.getStyle() != null)
				html.print(" style=\"%s\"", item.getStyle());
			if (item.getAlign() != null)
				html.print(" align=\"%s\"", item.getAlign());
			if (item.getRole() != null)
				html.print(" role=\"%s\"", item.getRole());

			html.println(">");
			for (IField obj : item.getFields()) {
				if (obj instanceof AbstractField) {
					AbstractField field = (AbstractField) obj;
					if (field.getTitle() != null && !"".equals(field.getTitle()))
						html.print("%s: ", field.getTitle());
					if (field instanceof IColumn)
						html.print(((IColumn) field).format(dataSource.getRecord()));
					else if (field instanceof AbstractField)
						outputField(html, (AbstractField) field);
					else
						throw new RuntimeException("暂不支持的数据类型：" + field.getClass().getName());
				}
			}
			html.println("</td>");
		}
		html.println("</tr>");
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

}
