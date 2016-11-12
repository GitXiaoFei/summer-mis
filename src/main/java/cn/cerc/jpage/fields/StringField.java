package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;

public class StringField extends AbstractField {
	// private static final Logger log = Logger.getLogger(Field.class);

	public StringField(DataView owner, String name, String field) {
		super(owner, name, 0);
		this.setField(field);
	}

	public StringField(DataView owner, String name, String field, int width) {
		super(owner, name, field, width);
	}

	@Override
	public String getText(Record rs) {
		return getDefaultText(rs);
	}

}
