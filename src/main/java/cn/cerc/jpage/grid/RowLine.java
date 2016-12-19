package cn.cerc.jpage.grid;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.fields.IField;

public class RowLine extends Component implements DataView {
	private List<IField> fields = new ArrayList<>();
	private DataView dataSource;
	private boolean visible = true;

	public RowLine(DataView dataSource) {
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
	public void addField(IField field) {
		fields.add(field);
	}

	@Override
	public Record getRecord() {
		return dataSource.getRecord();
	}

	public List<IField> getFields() {
		return fields;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
