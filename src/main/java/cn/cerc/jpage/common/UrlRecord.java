package cn.cerc.jpage.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class UrlRecord {
	private String site;
	private String name;
	private String title;
	private Map<String, String> params = new HashMap<>();

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
			if(value != null)
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

}
