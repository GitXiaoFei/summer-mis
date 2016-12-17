package cn.cerc.jpage.form;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.fields.IField;

public class FieldSets extends Component implements DataView {
	private DataView dataView;
	private List<AbstractField> fields = new ArrayList<>();

	public FieldSets(DataView dataView) {
		this.dataView = dataView;
	}

	@Override
	public void output(HtmlWriter html) {
		for (AbstractField field : fields) {
			field.output(html);
		}
	}

	@Override
	public void addField(IField field) {
		if (field instanceof AbstractField)
			fields.add((AbstractField) field);
		else
			throw new RuntimeException("不支持的数据类型：" + field.getClass().getName());
	}

	@Override
	public Record getRecord() {
		if (this.dataView == null)
			return null;
		return this.dataView.getRecord();
	}

	public List<AbstractField> getFields() {
		return this.fields;
	}

	public void remove(AbstractField field) {
		fields.remove(field);
	}
}
