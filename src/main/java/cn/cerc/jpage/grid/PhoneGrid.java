package cn.cerc.jpage.grid;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.DataSource;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.IField;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.fields.ExpendField;
import cn.cerc.jpage.other.BuildUrl;

// 手机专用表格
public class PhoneGrid extends AbstractGrid {
	private String CSSClass_Phone = "context";
	private List<PhoneLine> lines = new ArrayList<>();

	public PhoneGrid(Component owner) {
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
		if (dataSet.size() == 0)
			return;

		html.println(String.format("<ol class=\"%s\">", this.getCSSClass_Phone()));

		int i = pages.getBegin();
		while (i <= pages.getEnd()) {
			dataSet.setRecNo(i + 1);
			int flag = 0;
			html.println("<li>");
			for (PhoneLine line : this.lines) {
				if (line.isTable()) {
					if (flag == 0) {
						html.println("<table>");
						flag = 1;
					} else if (flag == 2) {
						html.println("</table>");
						html.println("<table>");
					}
				} else {
					if (flag == 1) {
						html.println("</table>");
						flag = 2;
					}
				}
				line.output(html);
			}
			if (flag == 1) {
				html.println("</table>");
			}
			html.println("</li>");
			i++;
		}
		html.println("</ol>");
		return;
	}

	public PhoneLine addLine() {
		PhoneLine line = new PhoneLine(this);
		lines.add(line);
		return line;
	}

	public class PhoneLine extends Component implements DataSource {
		private DataSource dataSource;
		private boolean Table = false;
		private String style;
		private ExpendField expender;

		private List<AbstractField> columns = new ArrayList<>();

		public PhoneLine(DataSource dataView) {
			this.dataSource = dataView;
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
			if (dataSource == null)
				throw new RuntimeException("dataView is null");
			DataSet dataSet = dataSource.getDataSet();
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
					build.buildUrl(dataSet.getCurrent(), url);
					if (!"".equals(url.getUrl())) {
						html.println("<a href=\"%s\">", url.getUrl());
						html.print(field.getText(dataSet.getCurrent()));
						html.println("</a>");
					} else {
						html.println(field.getText(dataSet.getCurrent()));
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
					DataSet dataSet = dataSource != null ? dataSource.getDataSet() : null;
					UrlRecord url = new UrlRecord();
					build.buildUrl(dataSet.getCurrent(), url);
					if (!"".equals(url.getUrl())) {
						html.println("<a href=\"%s\">", url.getUrl());
						outputColumn(field, html);
						html.println("</a>");
					} else {
						html.println(field.getText(dataSet.getCurrent()));
					}
				} else {
					outputColumn(field, html);
				}
				html.print("</span>");
			}
			html.print("</section>");
		}

		private void outputColumn(AbstractField field, HtmlWriter html) {
			DataSet dataSet = dataSource != null ? dataSource.getDataSet() : null;
			String name = field.getShortName();
			if (!"".equals(name))
				html.print(name + ": ");
			html.print(field.getText(dataSet.getCurrent()));
		}

		@Override
		public void output(HtmlWriter html) {
			if (this.Table)
				outputTableString(html);
			else
				outputListString(html);
		}

		@Override
		public void addField(IField field) {
			if (field instanceof AbstractField)
				columns.add((AbstractField) field);
		}

		public PhoneLine addItem(AbstractField... fields) {
			for (AbstractField field : fields)
				addField(field);
			return this;
		}

		public ExpendField getExpender() {
			return expender;
		}

		public void setExpender(ExpendField expender) {
			this.expender = expender;
		}

		@Override
		public DataSet getDataSet() {
			return dataSource.getDataSet();
		}
	}

	@Override
	public Component getExpender() {
		return this;
	}

	public String getCSSClass_Phone() {
		return CSSClass_Phone;
	}

	public void setCSSClass_Phone(String cSSClass_Phone) {
		CSSClass_Phone = cSSClass_Phone;
	}

}
