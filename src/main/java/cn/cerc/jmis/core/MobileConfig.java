package cn.cerc.jmis.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobileConfig {
	// 根站点
	private String rootSite = "http://127.0.0.1/";
	// 配置版本
	private String webVersion = "1.0.0";
	// 前台版本
	private String appVersion = "1.0.0";
	// 前台升级时下载地址
	private String appUpgrade = "http://127.0.0.1/download/android.apk";
	// 广告图片，最少必须一条记录！
	private List<String> adsImages = new ArrayList<>();
	// 首页地址，以及返回时是否可以按退出
	private Map<String, Boolean> homePages = new HashMap<>();
	// 取消息网址(暂未使用)
	private String msgService = "";

	public String getWebVersion() {
		return webVersion;
	}

	public void setWebVersion(String webVersion) {
		this.webVersion = webVersion;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppUpgrade() {
		return appUpgrade;
	}

	public void setAppUpgrade(String appUpgrade) {
		this.appUpgrade = appUpgrade;
	}

	public List<String> getAdsImages() {
		return adsImages;
	}

	public void setAdsImages(List<String> adsImages) {
		this.adsImages = adsImages;
	}

	public Map<String, Boolean> getHomePages() {
		return homePages;
	}

	public void setHomePages(Map<String, Boolean> homePages) {
		this.homePages = homePages;
	}

	public String getMsgService() {
		return msgService;
	}

	public void setMsgService(String msgService) {
		this.msgService = msgService;
	}

	public String getRootSite() {
		return rootSite;
	}

	public void setRootSite(String rootSite) {
		this.rootSite = rootSite;
	}
}
