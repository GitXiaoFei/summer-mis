package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.IColumn;
import cn.cerc.jpage.fields.editor.CheckEditor;
import cn.cerc.jpage.grid.DataGrid;
import cn.cerc.jpage.grid.extjs.ExtColumn;
import cn.cerc.jpage.other.SearchItem;

public class BooleanField extends AbstractField implements SearchItem, IColumn {
	private String trueText = "是";
	private String falseText = "否";
	private String title;
	private boolean search;
	private CheckEditor editor;

	public BooleanField(Component owner, String title, String field) {
		this(owner, title, field, 0);
	}

	public BooleanField(Component owner, String title, String field, int width) {
		super(owner, title, width);
		this.setField(field);
		this.setAlign("center");
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
		return dataSet.getBoolean(field) ? trueText : falseText;
	}

	public BooleanField setBooleanText(String trueText, String falseText) {
		this.trueText = trueText;
		this.falseText = falseText;
		return this;
	}

	@Override
	public void output(HtmlWriter html) {
		if (!this.search) {
			html.println(String.format("<label for=\"%s\">%s</label>", this.getId(), this.getName() + "："));
			writeInput(html);
			if (this.title != null)
				html.print("<label for=\"%s\">%s</label>", this.getId(), this.title);
		} else {
			writeInput(html);
			html.println(String.format("<label for=\"%s\">%s</label>", this.getId(), this.getName()));
		}
	}

	private void writeInput(HtmlWriter html) {
		html.print(String.format("<input type=\"checkbox\" id=\"%s\" name=\"%s\" value=\"1\"", this.getId(),
				this.getId()));
		boolean val = false;
		DataSet dataSet = dataSource != null ? dataSource.getDataSet() : null;
		if (dataSet != null)
			val = dataSet.getBoolean(field);
		if (val)
			html.print(" checked");
		if (this.isReadonly())
			html.print(" disabled");
		if (this.onclick != null)
			html.print(" onclick=\"%s\"", this.onclick);
		html.print(">");
	}

	@Override
	public String getTitle() {
		return title == null ? this.getName() : title;
	}

	public BooleanField setTitle(String title) {
		this.title = title;
		return this;
	}

	public boolean isSearch() {
		return search;
	}

	@Override
	public void setSearch(boolean search) {
		this.search = search;
	}

	@Override
	public ExtColumn getColumn() {
		ExtColumn column = super.getColumn();
		column.setEditor(null);
		return column;
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
	
	public CheckEditor getEditor() {
		if(editor == null)
			editor = new CheckEditor(this);
		return editor;
	}
}
