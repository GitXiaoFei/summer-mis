package cn.cerc.jui.vcl;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jui.vcl.columns.AbstractColumn;

public class BaseGrid extends Component {
	private List<AbstractColumn> columns = new ArrayList<>();
	private List<Row> rows = new ArrayList<>();
	private Row current;

	@Override
	public void addComponent(Component component) {
		if (component instanceof AbstractColumn) {
			columns.add((AbstractColumn) component);
		}
		if (component instanceof Row) {
			rows.add((Row) component);
		}
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<table>");
		html.print("<tr>");
		for (AbstractColumn column : columns) {
			html.print("<th>%s</th>", column.getTitle());
		}
		html.print("<tr>");

		for (Row row : rows) {
			html.print("<tr>");
			for (AbstractColumn column : columns) {
				html.print("<td>");
				Object value = row.getItems().get(column);
				html.print("%s", column.format(value));
				html.print("</td>");
			}
			html.print("<tr>");
		}

		html.print("</table>");
	}

	public void addItem() {
		current = new Row(this);
		rows.add(current);
	}

	public Row getCurrent() {
		return current;
	}

	public void setCurrent(Row current) {
		this.current = current;
	}

}
