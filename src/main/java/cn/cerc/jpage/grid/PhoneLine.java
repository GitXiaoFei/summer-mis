package cn.cerc.jpage.grid;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.BuildUrl;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.common.Expender;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jui.vcl.columns.IColumn;

public class PhoneLine extends Component implements DataView {
	private DataView dataView;
	private boolean Table = false;
	private String style;
	private Expender expender;

	private List<AbstractField> columns = new ArrayList<>();

	public PhoneLine(DataView dataView) {
		this.dataView = dataView;
	}

	public PhoneLine setStyle(String style) {
		this.style = style;
		return this;
	}

	public String getStyle() {
		return style;
	}

	public List<AbstractField> getColumns() {
		return columns;
	}

	public boolean isTable() {
		return Table;
	}

	public PhoneLine setTable(boolean table) {
		Table = table;
		return this;
	}

	private void outputTableString(HtmlWriter html) {
		if (dataView == null)
			throw new RuntimeException("dataView is null");
		Record dataSet = dataView.getRecord();
		if (dataSet == null)
			throw new RuntimeException("dataSet is null");
		html.print("<tr");
		if (this.expender != null)
			html.print(String.format(" role=\"%s\" style=\"display: none;\"", expender.getHiddenId()));
		html.print(">");
		for (AbstractField field : columns) {
			html.print("<td");
			if (columns.size() == 1)
				html.print(" colspan=2");
			html.print(">");

			BuildUrl build = field.getBuildUrl();
			if (build != null) {
				String name = field.getShortName();
				if (!"".equals(name))
					html.print(name + ": ");
				UrlRecord url = new UrlRecord();
				build.buildUrl(dataSet, url);
				if (!"".equals(url.getUrl())) {
					html.println("<a href=\"%s\">", url.getUrl());
					html.print(field.getText(dataSet));
					html.println("</a>");
				} else {
					html.println(field.getText(dataSet));
				}
			} else {
				outputColumn(field, html);
			}

			html.print("</td>");
		}
		html.print("</tr>");
	}

	public void outputListString(HtmlWriter html) {
		html.print("<section>");
		for (AbstractField field : columns) {
			html.print("<span");
			if (field.getCSSClass_phone() != null)
				html.print(String.format(" class=\"%s\"", field.getCSSClass_phone()));
			html.print(">");
			BuildUrl build = field.getBuildUrl();
			if (build != null) {
				Record dataSet = dataView != null ? dataView.getRecord() : null;
				UrlRecord url = new UrlRecord();
				build.buildUrl(dataSet, url);
				if (!"".equals(url.getUrl())) {
					html.println("<a href=\"%s\">", url.getUrl());
					outputColumn(field, html);
					html.println("</a>");
				} else {
					html.println(field.getText(dataSet));
				}
			} else {
				outputColumn(field, html);
			}
			html.print("</span>");
		}
		html.print("</section>");
	}

	private void outputColumn(AbstractField field, HtmlWriter html) {
		Record dataSet = dataView != null ? dataView.getRecord() : null;
		String name = field.getShortName();
		if (!"".equals(name))
			html.print(name + ": ");
		html.print(field.getText(dataSet));
	}

	@Override
	public void output(HtmlWriter html) {
		if (this.Table)
			outputTableString(html);
		else
			outputListString(html);
	}

	@Override
	public void addField(IColumn field) {
		if (field instanceof AbstractField)
			columns.add((AbstractField) field);
	}

	public PhoneLine addItem(AbstractField... fields) {
		for (AbstractField field : fields)
			addField(field);
		return this;
	}

	@Override
	public Record getRecord() {
		if (dataView == null)
			return null;
		return dataView.getRecord();
	}

	public Expender getExpender() {
		return expender;
	}

	public void setExpender(Expender expender) {
		this.expender = expender;
	}
}
