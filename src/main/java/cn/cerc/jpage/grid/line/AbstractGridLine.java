package cn.cerc.jpage.grid.line;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.DataSource;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.IField;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.grid.RowCell;
import cn.cerc.jpage.other.BuildUrl;

public abstract class AbstractGridLine extends Component implements DataSource {
	private List<IField> fields = new ArrayList<>();
	private List<RowCell> cells = new ArrayList<>();
	protected DataSource dataSource;

	public AbstractGridLine(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void addComponent(Component component) {
		super.addComponent(component);
		if (component instanceof AbstractField) {
			AbstractField field = (AbstractField) component;
			field.setVisible(false);
		}
	}

	@Override
	public abstract void addField(IField field);

	@Override
	public Record getRecord() {
		return dataSource.getRecord();
	}

	public abstract void output(HtmlWriter html, DataSet dataSet, int lineNo);

	protected void outputField(HtmlWriter html, AbstractField field) {
		Record record = dataSource.getRecord();

		BuildUrl build = field.getBuildUrl();
		if (build != null) {
			UrlRecord url = new UrlRecord();
			build.buildUrl(record, url);
			if (!"".equals(url.getUrl())) {
				html.print("<a href=\"%s\"", url.getUrl());
				if (url.getTitle() != null)
					html.print(" title=\"%s\"", url.getTitle());
				html.println(">%s</a>", field.getText(record));
			} else {
				html.println(field.getText(record));
			}
		} else {
			html.print(field.getText(record));
		}
	}

	public List<IField> getFields() {
		return fields;
	}

	public RowCell getCell(int index) {
		return cells.get(index);
	}

	protected List<RowCell> getCells() {
		return cells;
	}
}
