package cn.cerc.jui.vcl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jui.vcl.columns.AbstractColumn;

public class ObjectGrid extends Component {
	private List<AbstractColumn> columns = new ArrayList<>();
	private List<RowData> rows = new ArrayList<>();
	private RowData current;

	@Override
	public void addComponent(Component component) {
		if (component instanceof AbstractColumn) {
			columns.add((AbstractColumn) component);
		}
		if (component instanceof RowData) {
			rows.add((RowData) component);
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

		for (RowData row : rows) {
			html.print("<tr>");
			for (AbstractColumn column : columns) {
				html.print("<td>");
				Object data = row.getData(column);
				html.print("%s", column.format(data));
				html.print("</td>");
			}
			html.print("<tr>");
		}

		html.print("</table>");
	}

	public void addItem() {
		current = new RowData();
		rows.add(current);
	}

	public RowData getCurrent() {
		return current;
	}

	public void setCurrent(RowData current) {
		this.current = current;
	}

	public class RowData extends Component {
		private Map<AbstractColumn, Object> items = new LinkedHashMap<>();

		public Object getData(AbstractColumn column) {
			return items.get(column);
		}

		public void addData(AbstractColumn column, Object data) {
			items.put(column, data);
		}
	}

}
