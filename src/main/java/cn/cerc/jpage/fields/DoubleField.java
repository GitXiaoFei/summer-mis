package cn.cerc.jpage.fields;

import java.text.DecimalFormat;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.IColumn;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.fields.editor.ColumnEditor;
import cn.cerc.jpage.grid.lines.AbstractGridLine;

public class DoubleField extends AbstractField implements IColumn {
	private ColumnEditor editor;

	public DoubleField(Component owner, String title, String field) {
		super(owner, title, 4);
		this.setField(field);
		this.setAlign("right");
	}

	public DoubleField(Component owner, String title, String field, int width) {
		super(owner, title, width);
		this.setField(field);
		this.setAlign("right");
	}

	@Override
	public String getText(Record dataSet) {
		if (dataSet == null)
			return null;
		if (buildText != null) {
			HtmlWriter html = new HtmlWriter();
			buildText.outputText(dataSet, html);
			return html.toString();
		}
		try {
			double val = dataSet.getDouble(field);
			DecimalFormat df = new DecimalFormat("0.####");
			return df.format(val);
		} catch (NumberFormatException e) {
			return "0";
		}
	}

	@Override
	public Title createTitle() {
		Title title = super.createTitle();
		title.setType("float");
		return title;
	}

	@Override
	public String format(Object value) {
		if (!(value instanceof Record))
			return value.toString();

		Record ds = (Record) value;
		if (this.isReadonly()) {
			if (buildUrl != null) {
				HtmlWriter html = new HtmlWriter();
				UrlRecord url = new UrlRecord();
				buildUrl.buildUrl(ds, url);
				if (!"".equals(url.getUrl())) {
					html.print("<a href=\"%s\"", url.getUrl());
					if (url.getTitle() != null) {
						html.print(" title=\"%s\"", url.getTitle());
					}
					if (url.getTarget() != null) {
						html.print(" target=\"%s\"", url.getTarget());
					}
					html.println(">%s</a>", getText(ds));
				} else
					html.println(getText(ds));
				return html.toString();
			} else
				return getText(ds);
		}
		if (!(this.getOwner() instanceof AbstractGridLine))
			return getText(ds);

		return getEditor().format(ds);
	}

	public ColumnEditor getEditor() {
		if (editor == null)
			editor = new ColumnEditor(this);
		return editor;
	}
}
