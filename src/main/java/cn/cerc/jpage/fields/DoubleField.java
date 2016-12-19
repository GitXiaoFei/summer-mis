package cn.cerc.jpage.fields;

import static cn.cerc.jdb.other.utils.roundTo;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.grid.DataGrid;
import cn.cerc.jui.vcl.columns.IColumn;

public class DoubleField extends AbstractField implements IColumn {
	private ColumnEditor editor;
	private int scale = -4;

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
			String val = "" + roundTo(dataSet.getDouble(field), scale);
			if (buildUrl != null) {
				HtmlWriter html = new HtmlWriter();
				UrlRecord url = new UrlRecord();
				buildUrl.buildUrl(dataSet, url);
				if (!"".equals(url.getUrl())) {
					html.print("<a href=\"%s\"", url.getUrl());
					if (url.getTitle() != null)
						html.print(" title=\"%s\"", url.getTitle());
					html.println(">%s</a>", val);
				}
				return html.toString();
			}
			return val;
		} catch (NumberFormatException e) {
			return "0";
		}
	}

	public int getScale() {
		return scale;
	}

	public DoubleField setScale(int scale) {
		this.scale = scale;
		return this;
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
		if (this.isReadonly())
			return getText(ds);

		if (!(this.getOwner() instanceof DataGrid))
			return getText(ds);

		return getEditor().format(ds);
	}

	public ColumnEditor getEditor() {
		if (editor == null)
			editor = new ColumnEditor(this);
		return editor;
	}
}
