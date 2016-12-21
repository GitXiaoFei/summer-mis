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

	// 启动页（每次启动时显示）
	private String startImage;
	// 欢迎页(第一次显示，至少1个)
	private List<String> welcomeImages = new ArrayList<>();
	// 广告页（在第一次时不显示，在启动页之后显示），可为空，可多个
	private List<String> adImages = new ArrayList<>();

	// 首页地址，以及返回时是否可以按退出
	private Map<String, Boolean> homePages = new HashMap<>();
	// 推送服务商代码
	private String msgService = "www.jiguang.cn";
	// 推送服务商配置，如AppKey
	private String msgConfig = "";
	// 消息管理页
	private String msgManage = "forms/FrmMessages";
	// 静态文件列表
	private List<String> cacheFiles = new ArrayList<>();

	// 最后更新时间，格式范例：2016-01-01 01:01:01
	private String lastModified;
	// Debug标志, true: 显示自定义首页框，否则不允许更改默认首页
	private boolean debug;

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

	public List<String> getAdImages() {
		return adImages;
	}

	public Map<String, Boolean> getHomePages() {
		return homePages;
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

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getMsgConfig() {
		return msgConfig;
	}

	public void setMsgConfig(String msgConfig) {
		this.msgConfig = msgConfig;
	}

	public List<String> getWelcomeImages() {
		return welcomeImages;
	}

	public String getStartImage() {
		return startImage;
	}

	public void setStartImage(String startImage) {
		this.startImage = startImage;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getMsgManage() {
		return msgManage;
	}

	public void setMsgManage(String msgManage) {
		this.msgManage = msgManage;
	}

	public List<String> getCacheFiles() {
		return cacheFiles;
	}

	public void setCacheFiles(List<String> cacheFiles) {
		this.cacheFiles = cacheFiles;
	}
}
