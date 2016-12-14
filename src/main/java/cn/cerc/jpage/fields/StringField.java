package cn.cerc.jpage.fields;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.grid.DataGrid;
import cn.cerc.jui.vcl.columns.IColumn;

public class StringField extends AbstractField {
	// private static final Logger log = Logger.getLogger(Field.class);
	private boolean init = false;
	private DataSet dataSet;
	private List<IColumn> columns;
	private String onUpdate;

	public StringField(Component owner, String name, String field) {
		super(owner, name, 0);
		this.setField(field);
	}

	public StringField(Component owner, String name, String field, int width) {
		super(owner, name, 0);
		this.setField(field);
		this.setWidth(width);
	}

	public String getOnUpdate() {
		return onUpdate;
	}

	public StringField setOnUpdate(String onUpdate) {
		this.onUpdate = onUpdate;
		return this;
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

		if (!this.init) {
			DataGrid grid = (DataGrid) this.getOwner();
			if (grid.getPrimaryKey() == null)
				throw new RuntimeException("BaseGrid.primaryKey is null");

			dataSet = grid.getDataSet();
			columns = new ArrayList<>();
			for (IColumn src : grid.getColumns()) {
				if (src instanceof StringField) {
					if (!((StringField) src).isReadonly())
						columns.add(src);
				}
			}
			this.init = true;
		}

		HtmlWriter html = new HtmlWriter();
		html.print("<input");
		html.print(" id='%s'", this.getDataId());
		html.print(" type='text'");
		html.print(" name='%s'", this.getField());
		html.print(" value='%s'", data);
		html.print(" data-focus='[%s]'", this.getDataFocus());
		html.print(" data-%s='[%s]'", this.getField(), data);
		html.print(" onkeydown='tableDirection(event,this)'");
		if (this.getOnclick() != null) {
			html.print(" onclick=\"%s\"", this.getOnclick());
		} else
			html.print(" onclick='this.select()'");
		if (onUpdate != null)
			html.print(" oninput=\"tableOnChanged(this,'%s')\"", onUpdate);
		else
			html.print(" oninput='tableOnChanged(this)'");
		html.println("/>");
		return html.toString();
	}

	private String getDataId() {
		int recNo = dataSet.getRecNo();
		int colNo = columns.indexOf(this);
		String selfId = String.format("%d_%d", recNo, colNo);
		return selfId;
	}

	private String getDataFocus() {
		int recNo = dataSet.getRecNo();
		int colNo = columns.indexOf(this);

		String prior = recNo > 1 ? String.format("%d_%d", recNo - 1, colNo) : "0";
		String next = recNo < dataSet.size() ? String.format("%d_%d", recNo + 1, colNo) : "0";
		String left = colNo > 0 ? String.format("%d_%d", recNo, colNo - 1) : "0";
		String right = colNo < columns.size() - 1 ? String.format("%d_%d", recNo, colNo + 1) : "0";
		return String.format("\"%s\",\"%s\",\"%s\",\"%s\"", left, prior, right, next);
	}
}
