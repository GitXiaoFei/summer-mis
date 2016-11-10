package cn.cerc.jpage.form;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.fields.Field;

public class FieldSets extends Component implements DataView {
	private DataView dataView;
	private List<Field> fields = new ArrayList<>();

	public FieldSets(DataView dataView) {
		this.dataView = dataView;
	}

	@Override
	public void output(HtmlWriter html) {
		for (Field field : fields) {
			field.output(html);
		}
	}

	@Override
	public void addField(Field field) {
		fields.add(field);
	}

	@Override
	public Record getRecord() {
		if (this.dataView == null)
			return null;
		return this.dataView.getRecord();
	}

	public List<Field> getFields() {
		return this.fields;
	}

	public void remove(Field field) {
		fields.remove(field);
	}
}
