package cn.cerc.jpage.fields;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.grid.DataGrid;
import cn.cerc.jui.vcl.columns.IColumn;

public class ColumnEditor {
	private AbstractField owner;
	private boolean init = false;
	private DataSet dataSet;
	private List<IField> columns;
	private String onUpdate;

	public ColumnEditor(AbstractField owner) {
		this.owner = owner;
	}

	public String getOnUpdate() {
		return onUpdate;
	}

	public void setOnUpdate(String onUpdate) {
		this.onUpdate = onUpdate;
	}

	public String format(Record ds) {
		String data = ds.getString(owner.getField());

		if (!this.init) {
			DataGrid grid = (DataGrid) owner.getOwner();
			if (grid.getPrimaryKey() == null)
				throw new RuntimeException("BaseGrid.primaryKey is null");

			dataSet = grid.getDataSet();
			columns = new ArrayList<>();
			for (IField src : grid.getColumns()) {
				if (src instanceof IColumn) {
					if (((AbstractField) src).isReadonly())
						continue;
					if(src.getWidth() == 0)
						continue;
					columns.add(src);
				}
			}
			this.init = true;
		}

		HtmlWriter html = new HtmlWriter();
		html.print("<input");
		html.print(" id='%s'", this.getDataId());
		html.print(" type='text'");
		html.print(" name='%s'", owner.getField());
		html.print(" value='%s'", data);
		html.print(" data-focus='[%s]'", this.getDataFocus());
		html.print(" data-%s='[%s]'", owner.getField(), data);
		if (owner.getAlign() != null)
			html.print(" style='text-align:%s;'", owner.getAlign());
		html.print(" onkeydown='tableDirection(event,this)'");
		if (owner.getOnclick() != null) {
			html.print(" onclick=\"%s\"", owner.getOnclick());
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
		int colNo = columns.indexOf(owner);
		String selfId = String.format("%d_%d", recNo, colNo);
		return selfId;
	}

	private String getDataFocus() {
		int recNo = dataSet.getRecNo();
		int colNo = columns.indexOf(owner);

		String prior = recNo > 1 ? String.format("%d_%d", recNo - 1, colNo) : "0";
		String next = recNo < dataSet.size() ? String.format("%d_%d", recNo + 1, colNo) : "0";
		String left = colNo > 0 ? String.format("%d_%d", recNo, colNo - 1) : "0";
		String right = colNo < columns.size() - 1 ? String.format("%d_%d", recNo, colNo + 1) : "0";
		return String.format("\"%s\",\"%s\",\"%s\",\"%s\"", left, prior, right, next);
	}
}
