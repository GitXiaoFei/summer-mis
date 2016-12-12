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
import cn.cerc.jui.vcl.columns.IColumn;

public class BaseGrid extends Grid {

	private String trId;

	public BaseGrid() {
		super();
		trId = "tr";
	}

	public BaseGrid(Component owner) {
		super(owner);
		trId = "tr";
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
		List<IColumn> columns = this.getColumns();

		double sumFieldWidth = 0;
		for (IColumn column : columns)
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
		for (IColumn column : columns) {
			if (column.getWidth() > 0) {
				double val = roundTo(column.getWidth() / sumFieldWidth * 100, -2);
				html.println("<th width=\"%f%%\">%s</th>", val, column.getTitle());
			}
		}
		html.println("</tr>");
		int i = pages.getBegin();
		while (i <= pages.getEnd()) {
			dataSet.setRecNo(i + 1);
			int expendSum = 0;
			// 输出正常字段
			html.println("<tr");
			html.println(" id='%s'", trId + dataSet.getRecNo());
			html.println(">");
			for (IColumn column : columns) {
				if (column instanceof AbstractField) {
					AbstractField field = (AbstractField) column;
					if (field.getExpender() == null) {
						html.print("<td");
						if (field.getAlign() != null)
							html.print(" align=\"%s\"", field.getAlign());
						if (field.getField() != null)
							html.print(" role=\"%s\"", field.getField());
						html.print(">");
						outputField(html, field);
						html.println("</td>");
					} else {
						expendSum++;
					}
				} else {
					html.print("<td");
					if (column.getAlign() != null)
						html.print(" align=\"%s\"", column.getAlign());
					html.print(">");
					html.print(column.format(getDataSet().getCurrent()));
					html.println("</td>");
				}
			}
			html.println("</tr>");
			// 输出隐藏字段
			if (expendSum > 0) {
				html.println("<tr role=\"%d\" style=\"display:none\">", dataSet.getRecNo());
				html.println("<td colspan=\"%d\">", columns.size() - expendSum);
				for (IColumn column : columns) {
					if (column instanceof AbstractField) {
						AbstractField field = (AbstractField) column;
						if (field.getExpender() != null) {
							html.print("<span>");
							if (!"".equals(field.getName())) {
								html.print(field.getName());
								html.print(": ");
							}
							outputField(html, field);
							html.println("</span>");
						}
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
		} else if (field instanceof IColumn) {
			IColumn col = (IColumn) field;
			html.print(col.format(record));
		} else {
			html.print(field.getText(record));
		}
	}

	public String getTrId() {
		return trId;
	}

	public void setTrId(String trId) {
		this.trId = trId;
	}
}
