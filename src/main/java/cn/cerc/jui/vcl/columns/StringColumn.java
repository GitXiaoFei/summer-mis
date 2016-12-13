package cn.cerc.jui.vcl.columns;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.grid.BaseGrid;
import cn.cerc.jui.vcl.columns.AbstractColumn;
import cn.cerc.jui.vcl.columns.IColumn;

public class StringColumn extends AbstractColumn {
	private boolean init = false;
	private DataSet dataSet;
	private List<IColumn> columns;

	public StringColumn(Component owner) {
		super(owner);
	}

	public StringColumn(Component owner, String field, String title) {
		super(owner, field, title);
	}

	public void add(String value) {
		this.getGrid().getCurrent().addData(this, value);
	}

	@Override
	public String format(Object value) {
		if (!(value instanceof Record))
			return value.toString();

		Record ds = (Record) value;
		String data = ds.getString(this.getField());
		if (this.isReadonly())
			return data;

		if (!(this.getOwner() instanceof BaseGrid))
			return data;

		if (!this.init) {
			BaseGrid grid = (BaseGrid) this.getOwner();
			if (grid.getPrimaryKey() == null)
				throw new RuntimeException("BaseGrid.primaryKey is null");
			
			dataSet = grid.getDataSet();
			columns = new ArrayList<>();
			for (IColumn src : grid.getColumns()) {
				if (src instanceof StringColumn)
					columns.add(src);
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
		html.print(" onkeydown='tableDirection(event,this)'");
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
