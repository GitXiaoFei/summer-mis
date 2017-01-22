package cn.cerc.jpage.grid.lines;

import java.util.List;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jpage.core.DataSource;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.IColumn;
import cn.cerc.jpage.core.IField;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.grid.IColumnsManager;
import cn.cerc.jpage.grid.RowCell;

public class MasterGridLine extends AbstractGridLine {
	// private static final Logger log = Logger.getLogger(MasterGridLine.class);
	private String primaryKey;
	// 列管理器，用于支持自定义栏位
	private IColumnsManager manager;

	public MasterGridLine(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void output(HtmlWriter html, int lineNo) {
		DataSet dataSet = dataSource.getDataSet();
		html.print("<tr");
		html.print(" id='%s'", "tr" + dataSet.getRecNo());
		if (this.getPrimaryKey() != null)
			html.print(" data-rowid='%s'", dataSet.getString(this.getPrimaryKey()));
		html.println(">");
		for (RowCell cell : this.getOutputCells()) {
			IField obj = cell.getFields().get(0);
			html.print("<td");
			if (cell.getColSpan() > 1)
				html.print(" colspan=\"%d\"", cell.getColSpan());
			if (cell.getStyle() != null)
				html.print(" style=\"%s\"", cell.getStyle());
			else if (obj.getWidth() == 0)
				html.print(" style=\"%s\"", "display:none");

			if (cell.getAlign() != null)
				html.print(" align=\"%s\"", cell.getAlign());
			else if (obj.getAlign() != null)
				html.print(" align=\"%s\"", obj.getAlign());

			if (cell.getRole() != null)
				html.print(" role=\"%s\"", cell.getRole());
			else if (obj.getField() != null)
				html.print(" role=\"%s\"", obj.getField());

			html.println(">");
			if (obj instanceof AbstractField) {
				AbstractField field = (AbstractField) obj;
				if (field instanceof IColumn)
					html.print(((IColumn) field).format(dataSource.getDataSet().getCurrent()));
				else if (field instanceof AbstractField)
					outputField(html, (AbstractField) field);
				else
					throw new RuntimeException("暂不支持的数据类型：" + field.getClass().getName());
			}
			html.println("</td>");
		}
		html.println("</tr>");
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public void addField(IField field) {
		getFields().add(field);
		RowCell col;
		col = new RowCell();
		col.setAlign(field.getAlign());
		col.setRole(field.getField());
		getCells().add(col);
		col.addField(field);
	}

	@Override
	public boolean isReadonly() {
		return dataSource.isReadonly();
	}

	@Override
	public void updateValue(String id, String code) {
		dataSource.updateValue(id, code);
	}

	public IColumnsManager getManager() {
		return manager;
	}

	public void setManager(IColumnsManager manager) {
		this.manager = manager;
	}

	public List<RowCell> getOutputCells() {
		if (this.manager == null)
			return getCells();
		return manager.Reindex(super.getCells());
	}
}
