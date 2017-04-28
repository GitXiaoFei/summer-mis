package cn.cerc.jpage.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class UrlRecord {
	//数据
	private String site;
	//名称
	private String name;
	//标题
	private String title;
	//对象
	private String target;
	private Map<String, String> params = new HashMap<>();

	public UrlRecord() {

	}

	public UrlRecord(String site, String caption) {
		super();
		this.site = site;
		this.name = caption;
	}

	public UrlRecord addParam(String key, String value) {
		params.put(key, value);
		return this;
	}

	public String getSite() {
		return site;
	}

	public UrlRecord setSite(String site) {
		this.site = site;
		return this;
	}

	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return 请改为getName
	 */
	@Deprecated
	public String getCaption() {
		return name;
	}

	public UrlRecord setName(String name) {
		this.name = name;
		return this;
	}

	public String getUrl() {
		StringBuffer sl = new StringBuffer();
		if (site != null)
			sl.append(site);

		int i = 0;
		for (String key : params.keySet()) {
			i++;
			sl.append(i == 1 ? "?" : "&");
			sl.append(key);
			sl.append("=");
			String value = params.get(key);
			if (value != null)
				sl.append(encodeUTF8(value));
		}
		return sl.toString();
	}

	private String encodeUTF8(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
