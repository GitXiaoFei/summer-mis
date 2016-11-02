package cn.cerc.jmis.core;

public class MenuRecord {
	private String img = "";
	private String name;
	private String url;
	private int hrip;

	public MenuRecord() {

	}

	public MenuRecord(String name, String url, String img) {
		this.name = name;
		this.url = url;
		this.img = img;
	}

	public MenuRecord(MenuData item) {
		setHrip(item.isWin() && item.isWeb() ? 2 : item.isWin() ? 1 : 0);
		setUrl(item.getId());

		String str = item.getCaption();
		str = str.substring(str.indexOf("]") + 1);
		str = str.substring(str.indexOf("\\") + 1);

		setName(str);
		setImg("menu/" + item.getId() + ".png");
	}

	public String getImg() {
		return img;
	}

	public MenuRecord setImg(String img) {
		this.img = img;
		return this;
	}

	public String getName() {
		return name;
	}

	public MenuRecord setName(String name) {
		this.name = name;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public MenuRecord setUrl(String url) {
		this.url = url;
		return this;
	}

	public int getHrip() {
		return hrip;
	}

	public void setHrip(int hrip) {
		this.hrip = hrip;
	}

}
