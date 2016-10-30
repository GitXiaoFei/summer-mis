package cn.cerc.jmis.page;

public class ExportFile {
	private String service;
	private String key;

	public ExportFile(String service, String key) {
		super();
		this.service = service;
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

}
