package cn.cerc.jui.vcl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jui.vcl.columns.AbstractColumn;
import cn.cerc.jui.vcl.columns.CheckBoxColumn;
import cn.cerc.jui.vcl.columns.ConvertColumn;
import cn.cerc.jui.vcl.columns.HideColumn;
import cn.cerc.jui.vcl.columns.IndexColumn;
import cn.cerc.jui.vcl.columns.LinkColumn;
import cn.cerc.jui.vcl.columns.OperatingColumn;

public class DBGrid extends Component implements DataView {
	private static final Logger log = Logger.getLogger(DBGrid.class);
	private List<AbstractColumn> columns = new ArrayList<>();
	private HttpServletRequest request;
	private DataSet dataSet;

	public DBGrid(Component owner) {
		super(owner);
	}

	@Override
	public void addComponent(Component component) {
		if (component instanceof AbstractColumn) {
			columns.add((AbstractColumn) component);
		}
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<table class='tbl'>");
		html.print("<tr>");
		for (AbstractColumn column : columns) {
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
		html.print("<tr>");

		html.print("<tbody class='t-body tc'>");
		for (int i = 0; i < dataSet.size(); i++) {
			Record record = dataSet.getRecords().get(i);
			html.print("<tr>");
			for (AbstractColumn column : columns) {
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
			html.print("<tr>");
		}
		html.print("</tbody>");

		html.print("</table>");
	}

	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void addField(AbstractField field) {
		log.warn("此函数未实现，不能调用！");
	}

	@Override
	public Record getRecord() {
		return dataSet.getCurrent();	}
}
