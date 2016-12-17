package cn.cerc.jpage.grid;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.fields.IField;

public class ExpenderBox extends Component implements DataView {
	private DataView dataSource;

	public ExpenderBox(DataView dataSource) {
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

	}

	@Override
	public Record getRecord() {
		return dataSource.getRecord();
	}
}
