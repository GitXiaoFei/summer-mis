package cn.cerc.jui.vcl.columns;

public class Image {
	private String name;
	private String url;
	private int width=16;
	private int height=16;
	
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Image(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}
	
}
