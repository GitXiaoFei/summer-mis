package cn.cerc.jpage.grid.columns;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.fields.AbstractField;

public class FieldAsColumn extends AbstractColumn {
	private AbstractField link;

	public FieldAsColumn(Component owner) {
		super(owner);
	}

	public AbstractField getLink() {
		return link;
	}

	public void setLink(AbstractField link) {
		this.link = link;
	}

	@Override
	public String format(Object value) {
		Record rs = (Record) value;
		return link.getText(rs);
	}

	@Override
	public String getTitle() {
		return link.getName();
	}
}
