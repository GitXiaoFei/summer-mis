package cn.cerc.jui.vcl.columns;

import cn.cerc.jpage.fields.IField;

public interface IColumn extends IField {

	public String format(Object value);

	default public int getWidth() {
		return 1;
	}
}
