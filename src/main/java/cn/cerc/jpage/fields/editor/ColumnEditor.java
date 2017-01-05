package cn.cerc.jpage.fields.editor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.IColumn;
import cn.cerc.jpage.core.IField;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.grid.DataGrid;
import cn.cerc.jpage.grid.lines.AbstractGridLine;
import cn.cerc.jpage.grid.lines.MasterGridLine;

public class ColumnEditor {
	private AbstractField owner;
	private boolean init = false;
	private DataSet dataSet;
	private List<IField> columns;
	private String onUpdate;
	private List<String> dataField = new ArrayList<>(); // 设置的字段列表
	private AbstractGridLine gridLine;

	public ColumnEditor(AbstractField owner) {
		this.owner = owner;
		if (!(owner.getOwner() instanceof AbstractGridLine))
			throw new RuntimeException("不支持的数据类型：" + owner.getOwner().getClass().getName());
		gridLine = (AbstractGridLine) (owner.getOwner());
	}

	public String getOnUpdate() {
		return onUpdate;
	}

	public void setOnUpdate(String onUpdate) {
		this.onUpdate = onUpdate;
	}

	public String format(Record ds) {
		String data = ds.getString(owner.getField());
		if (ds.getField(owner.getField()) instanceof Double) {
			DecimalFormat df = new DecimalFormat("0.####");
			data = df.format(ds.getDouble(owner.getField()));
		}

		if (!this.init) {
			dataSet = gridLine.getDataSet();
			columns = new ArrayList<>();
			for (IField field : gridLine.getFields()) {
				if (field instanceof IColumn) {
					if (((AbstractField) field).isReadonly())
						continue;
					if (field.getWidth() == 0)
						continue;
					columns.add(field);
				}
			}
			if (gridLine.getOwner() instanceof DataGrid) {
				DataGrid grid = (DataGrid) gridLine.getOwner();
				if (columns.size() > 0 && grid.getPrimaryKey() == null)
					throw new RuntimeException("BaseGrid.primaryKey is null");
			}
			this.init = true;
		}
		HtmlWriter html = new HtmlWriter();
		html.print("<input");
		if (gridLine instanceof MasterGridLine)
			html.print(" id='%s'", this.getDataId());
		else
			html.print(" style=\"width:80%;\"");
		html.print(" type='text'");
		html.print(" name='%s'", owner.getField());
		html.print(" value='%s'", data);
		html.print(" data-%s='[%s]'", owner.getField(), data);
		if (gridLine instanceof MasterGridLine) {
			html.print(" data-focus='[%s]'", this.getDataFocus());
			if (owner.getAlign() != null)
				html.print(" style='text-align:%s;'", owner.getAlign());
			if (owner.getOnclick() != null) {
				html.print(" onclick=\"%s\"", owner.getOnclick());
			} else
				html.print(" onclick='this.select()'");
		}
		html.print(" onkeydown='tableDirection(event,this)'");
		if (dataField.size() > 0) {
			for (String field : dataField) {
				html.print(" data-%s='%s'", field, ds.getString(field));
			}
		}
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

	public AbstractGridLine getGridLine() {
		return gridLine;
	}

	public void setGridLine(AbstractGridLine gridLine) {
		this.gridLine = gridLine;
	}

	/**
	 * 给元素设置data-*属性
	 * 
	 * @return 要设置的字段列表
	 */
	public List<String> getDataField() {
		return dataField;
	}
}
