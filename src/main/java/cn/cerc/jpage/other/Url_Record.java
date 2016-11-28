package cn.cerc.jpage.other;

public class Url_Record {
	private String url;
	private String caption;
	private String title;

	public Url_Record() {

	}

	public Url_Record(String url, String caption) {
		super();
		this.url = url;
		this.caption = caption;
	}

	public String getUrl() {
		return url;
	}

	public Url_Record setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getCaption() {
		return caption;
	}

	public Url_Record setCaption(String caption) {
		this.caption = caption;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
