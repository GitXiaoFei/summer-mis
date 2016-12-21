package cn.cerc.jpage.grid;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.DataSource;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.IColumn;
import cn.cerc.jpage.core.IField;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.grid.columns.CheckBoxColumn;
import cn.cerc.jpage.grid.columns.ConvertColumn;
import cn.cerc.jpage.grid.columns.FieldAsColumn;
import cn.cerc.jpage.grid.columns.HideColumn;
import cn.cerc.jpage.grid.columns.IndexColumn;
import cn.cerc.jpage.grid.columns.LinkColumn;
import cn.cerc.jpage.grid.columns.OperatingColumn;

public class SimpleGrid extends Component implements DataSource {
	// private static final Logger log = Logger.getLogger(DBGrid.class);
	private List<IColumn> columns = new ArrayList<>();
	private HttpServletRequest request;
	private DataSet dataSet;
	// 支持表格分页
	private MutiPage pages = new MutiPage();

	public SimpleGrid(Component owner) {
		super(owner);
	}

	@Override
	public void addComponent(Component component) {
		if (component instanceof IColumn) {
			columns.add((IColumn) component);
		}
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<table class='tbl'>");
		html.print("<tr>");
		for (IColumn column : columns) {
			if (column instanceof HideColumn) {
				html.print("<th class='t-head tc hide'>%s</th>", column.getTitle());
			} else if (column instanceof CheckBoxColumn) {
				CheckBoxColumn checkbox = (CheckBoxColumn) column;
				html.print("<th class='t-head tc'>%s", column.getTitle());
				html.print("<input type='checkbox' onclick=\"fnChkeckByName('%s',this)\" />", checkbox.getName());
				html.print("</th>");
			} else {
				html.print("<th class='t-head tc'>%s</th>", column.getTitle());
			}
		}
		html.println("<tr>");

		html.println("<tbody class='t-body tc'>");
		if (dataSet.size() > 0) {
			for (int i = pages.getBegin(); i <= pages.getEnd(); i++) {
				dataSet.setRecNo(i + 1);
				Record record = dataSet.getCurrent();
				html.print("<tr>");
				for (IColumn column : columns) {
					if (column instanceof HideColumn || column instanceof ConvertColumn
							|| column instanceof OperatingColumn || column instanceof LinkColumn
							|| column instanceof CheckBoxColumn) {
						html.print(column.format(record));
					} else if (column instanceof IndexColumn) {// 序号
						html.print("<td>%s</td>", i + 1);
					} else {
						html.print("<td>");
						html.print("%s", column.format(record));
						html.print("</td>");
					}
				}
				html.println("<tr>");
			}
		}
		html.println("</tbody>");
		html.println("</table>");
	}

	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
		pages.setDataSet(dataSet);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
		pages.setRequest(request);
	}

	@Override
	public void addField(IField field) {
		if (field instanceof AbstractField) {
			FieldAsColumn column = new FieldAsColumn(this);
			column.setLink((AbstractField) field);
		}
	}

	public MutiPage getPages() {
		return pages;
	}

	@Override
	public DataSet getDataSet() {
		return dataSet;
	}
}
