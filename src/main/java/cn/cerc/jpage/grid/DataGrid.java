package cn.cerc.jpage.grid;

import static cn.cerc.jdb.other.utils.roundTo;

import java.util.List;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.BuildUrl;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.fields.IField;
import cn.cerc.jui.vcl.columns.IColumn;

public class DataGrid extends AbstractGrid {
	private IColumnsManager manager;
	// 扩展对象
	private ExpenderBox expender;

	public DataGrid(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<div class='scrollArea'>");
		if (this.getDataSet().size() > 0) {
			if (form != null) {
				form.outHead(html);
				outputGrid(html);
				form.outFoot(html);
			} else {
				outputGrid(html);
			}
		}
		html.print("</div>");
	}

	@Override
	public void outputGrid(HtmlWriter html) {
		DataSet dataSet = this.getDataSet();
		MutiPage pages = this.getPages();
		List<IField> columns = this.getColumns();
		if (manager != null)
			columns = manager.Reindex(columns);

		double sumFieldWidth = 0;
		for (IField column : columns)
			sumFieldWidth += column.getWidth();

		if (sumFieldWidth < 0)
			throw new RuntimeException("总列宽不允许小于1");
		if (sumFieldWidth > 60)
			throw new RuntimeException("总列宽不允许大于60");

		html.print("<table class=\"%s\"", this.getCSSClass_PC());
		if (this.getCSSStyle() != null)
			html.print(" style=\"%s\"", this.getCSSStyle());
		html.println(">");

		html.println("<tr>");
		for (IField column : columns) {
			html.println("<th");
			if (column.getWidth() == 0)
				html.print(" style=\"display:none\"");
			else {
				double val = roundTo(column.getWidth() / sumFieldWidth * 100, -2);
				html.print(" width=\"%f%%\"", val);
			}
			html.print(">");
			html.print(column.getTitle());
			html.println("</th>");
		}
		html.println("</tr>");
		int i = pages.getBegin();
		while (i <= pages.getEnd()) {
			dataSet.setRecNo(i + 1);
			// 输出正常字段
			html.println("<tr");
			html.println(" id='%s'", "tr" + dataSet.getRecNo());
			if (this.getPrimaryKey() != null)
				html.println(" data-rowid='%s'", dataSet.getString(this.getPrimaryKey()));
			html.println(">");
			for (IField field : columns) {
				html.print("<td");
				if (field.getWidth() == 0)
					html.print(" style=\"display:none\"");
				if (field.getAlign() != null)
					html.print(" align=\"%s\"", field.getAlign());
				if (field.getField() != null)
					html.print(" role=\"%s\"", field.getField());
				html.print(">");
				
				if (field instanceof IColumn)
					html.print(((IColumn) field).format(getDataSet().getCurrent()));
				else if (field instanceof AbstractField)
					outputField(html, (AbstractField) field);
				else
					throw new RuntimeException("暂不支持的数据类型：" + field.getClass().getName());
				
				html.print("</td>");
			}
			html.println("</tr>");
			
			// 输出隐藏字段
			if (this.getExpender().getComponents().size() > 0) {
				html.println("<tr role=\"%d\" style=\"display:none\">", dataSet.getRecNo());
				html.println("<td colspan=\"%d\">", columns.size());
				for (Component column : this.getExpender().getComponents()) {
					if (column instanceof AbstractField) {
						AbstractField field = (AbstractField) column;
						html.print("<span>");
						if (!"".equals(field.getName())) {
							html.print(field.getName());
							html.print(": ");
						}
						outputField(html, field);
						html.println("</span>");
					}
				}
				html.println("</tr>");
			}
			// 下一行
			i++;
		}
		html.println("</table>");
		return;
	}

	private void outputField(HtmlWriter html, AbstractField field) {
		Record record = getDataSet().getCurrent();

		BuildUrl build = field.getBuildUrl();
		if (build != null) {
			UrlRecord url = new UrlRecord();
			build.buildUrl(record, url);
			if (!"".equals(url.getUrl())) {
				html.print("<a href=\"%s\"", url.getUrl());
				if (url.getTitle() != null)
					html.print(" title=\"%s\"", url.getTitle());
				html.println(">%s</a>", field.getText(record));
			} else {
				html.println(field.getText(record));
			}
		} else {
			html.print(field.getText(record));
		}
	}

	public IColumnsManager getManager() {
		return manager;
	}

	public void setManager(IColumnsManager manager) {
		this.manager = manager;
	}

	@Override
	public Component getExpender() {
		if (expender == null)
			expender = new ExpenderBox(this);
		return expender;
	}
}
