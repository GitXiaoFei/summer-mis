package cn.cerc.jpage.common;

public class Url_Record {
	private String site;
	private String name;
	private String title;

	public Url_Record() {

	}

	public Url_Record(String url, String caption) {
		super();
		this.site = url;
		this.name = caption;
	}

	public String getUrl() {
		return site;
	}

	public Url_Record setSite(String url) {
		this.site = url;
		return this;
	}

	public String getCaption() {
		return name;
	}

	public Url_Record setName(String caption) {
		this.name = caption;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
