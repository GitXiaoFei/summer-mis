package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.grid.DataGrid;
import cn.cerc.jui.vcl.columns.IColumn;

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
		String data = ds.getString(this.getField());
		
		if (this.isReadonly())
			return data;

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
