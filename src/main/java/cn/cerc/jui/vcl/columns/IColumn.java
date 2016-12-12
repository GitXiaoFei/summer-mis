package cn.cerc.jui.vcl.columns;

public interface IColumn {
	public String getTitle();

	public String format(Object value);

	public int getWidth();
	
	public String getAlign();

}
