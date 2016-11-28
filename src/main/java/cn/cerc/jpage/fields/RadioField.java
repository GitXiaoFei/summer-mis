package cn.cerc.jpage.fields;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;

public class RadioField extends StringField {
	private List<String> items = new ArrayList<>();

	public RadioField(DataView owner, String name, String field, int width) {
		super(owner, name, field, width);
	}

	@Override
	public String getText(Record dataSet) {
		if (dataSet == null)
			return null;
		int val = dataSet.getInt(field);
		if (val < 0 || val > items.size() - 1)
			return "" + val;
		String result = items.get(val);
		if (result == null)
			return "" + val;
		else
			return result;
	}

	public RadioField add(String... items) {
		for (String item : items)
			this.items.add(item);
		return this;
	}
}
