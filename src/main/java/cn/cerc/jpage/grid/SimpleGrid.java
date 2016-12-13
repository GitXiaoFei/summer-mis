package cn.cerc.jpage.grid;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jui.vcl.columns.CheckBoxColumn;
import cn.cerc.jui.vcl.columns.ConvertColumn;
import cn.cerc.jui.vcl.columns.FieldAsColumn;
import cn.cerc.jui.vcl.columns.HideColumn;
import cn.cerc.jui.vcl.columns.IColumn;
import cn.cerc.jui.vcl.columns.IndexColumn;
import cn.cerc.jui.vcl.columns.LinkColumn;
import cn.cerc.jui.vcl.columns.OperatingColumn;

public class SimpleGrid extends Component implements DataView {
	// private static final Logger log = Logger.getLogger(DBGrid.class);
	private List<IColumn> columns = new ArrayList<>();
	private HttpServletRequest request;
	private DataSet dataSet;
	// 分页控制
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
		pages.setPageSize(10);
		for (int i = pages.getBegin(); i <= pages.getEnd(); i++) {
			dataSet.setRecNo(i + 1);
			Record record = dataSet.getCurrent();
			html.print("<tr>");
			for (IColumn column : columns) {
				if (column instanceof HideColumn || column instanceof ConvertColumn || column instanceof OperatingColumn
						|| column instanceof LinkColumn || column instanceof CheckBoxColumn) {
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
		html.println("</tbody>");

		html.println("</table>");
	}

	public void setDataSet(DataSet dataSet) {
		if (this.dataSet == dataSet)
			return;
		this.dataSet = dataSet;
		if (request == null)
			throw new RuntimeException("request is null");

		int pageno = 1;
		String tmp = request.getParameter("pageno");
		if (tmp != null && !tmp.equals("")) {
			pageno = Integer.parseInt(tmp);
		}
		pages.setRecordCount(dataSet.size());
		pages.setCurrent(pageno);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void addField(IColumn field) {
		if (field instanceof AbstractField) {
			FieldAsColumn column = new FieldAsColumn(this);
			column.setLink((AbstractField) field);
		}
	}

	@Override
	public Record getRecord() {
		return dataSet.getCurrent();
	}

	@Override
	public int getRecNo() {
		return dataSet.getRecNo();
	}

	public MutiPage getPages() {
		return pages;
	}
}
