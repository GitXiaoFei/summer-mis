package cn.cerc.jui.vcl.columns;

public interface IColumn {
	public String getTitle();

	public String getField();

	public String format(Object value);

	default public int getWidth() {
		return 1;
	}

	default public String getAlign() {
		return null;
	}

}
