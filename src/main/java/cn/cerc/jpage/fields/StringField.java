package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.IColumn;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.fields.editor.ColumnEditor;
import cn.cerc.jpage.grid.DataGrid;
import cn.cerc.jpage.grid.lines.ChildGridLine;

public class StringField extends AbstractField implements IColumn {
	// private static final Logger log = Logger.getLogger(Field.class);
	private ColumnEditor editor;

	public StringField(Component owner, String name, String field) {
		super(owner, name, 0);
		this.setField(field);
	}

	public StringField(Component owner, String name, String field, int width) {
		super(owner, name, 0);
		this.setField(field);
		this.setWidth(width);
	}

	@Override
	public String getText(Record rs) {
		return getDefaultText(rs);
	}

	@Override
	public String format(Object value) {
		if (!(value instanceof Record))
			return value.toString();

		Record ds = (Record) value;
		String data = getDefaultText(ds);

		if (this.isReadonly()) {
			if (buildUrl != null) {
				HtmlWriter html = new HtmlWriter();
				UrlRecord url = new UrlRecord();
				buildUrl.buildUrl(ds, url);
				if (!"".equals(url.getUrl())) {
					html.print("<a href=\"%s\"", url.getUrl());
					if (url.getTitle() != null)
						html.print(" title=\"%s\"", url.getTitle());
					html.println(">%s</a>", data);
				}
				return html.toString();
			} else
				return data;
		}

		if (this.getOwner() instanceof ChildGridLine){
			HtmlWriter html = new HtmlWriter();
			html.print("<input");
			html.print(" id='%s'", this.getId());
			html.print(" type='text'");
			html.print(" name='%s'", this.getField());
			html.print(" value='%s'", data);
			html.print(" oninput='tableOnChanged(this)'");
			html.print(" style='width:80%;");
			if (this.getAlign() != null)
				html.print(" text-align:%s;", this.getAlign());
			html.println("'/>");
			return html.toString();
		}
		if (!(this.getOwner() instanceof DataGrid))
			return data;

		return getEditor().format(ds);
	}

	public ColumnEditor getEditor() {
		if (editor == null)
			editor = new ColumnEditor(this);
		return editor;
	}
}
