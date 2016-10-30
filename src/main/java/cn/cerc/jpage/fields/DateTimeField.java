package cn.cerc.jpage.fields;

import cn.cerc.jpage.common.DataView;

public class DateTimeField extends StringField {

	public DateTimeField(DataView owner, String name, String field) {
		super(owner, name, field, 14);
	}
}
