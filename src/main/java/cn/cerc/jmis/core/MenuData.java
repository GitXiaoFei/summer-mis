package cn.cerc.jmis.core;

import com.google.gson.Gson;

public class MenuData {
	private String clazz;
	// 页面类名代码，用于css定位
	private String id = "";
	// 页面标题
	private String caption;
	// 页面描述
	private String describe;
	private boolean security;
	private String parent = "";
	// 页面图标名称
	private String image;
	// 授权权限代码
	private String proccode;
	// 适用版本
	private String versions;
	// 页面版面代码，用于排版定位
	private String formNo = "000";
	private boolean web; // 是否支持Web调用
	private boolean win; // 是否支持Window调用
	private boolean hide; // 是否隐藏菜单
	private String process; // Web化进度
	private boolean folder; // 是否为目录结构
	private boolean custom; // 是否客制化菜单
	private String funcCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id.indexOf('.') > -1)
			throw new RuntimeException("error id: " + id);
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		if (caption != null) {
			String[] captions = caption.split("\\\\");
			this.caption = captions[captions.length - 1];
		} else
			this.caption = "";
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public boolean isSecurity() {
		return security;
	}

	public void setSecurity(boolean security) {
		this.security = security;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getProccode() {
		return proccode;
	}

	public void setProccode(String proccode) {
		this.proccode = proccode;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public String getVersions() {
		return versions;
	}

	public void setVersions(String versions) {
		this.versions = versions;
	}

	public String getFormNo() {
		return formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	public boolean isWeb() {
		return web;
	}

	public void setWeb(boolean web) {
		this.web = web;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public boolean getHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public boolean getFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	public boolean getCustom() {
		return custom;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}
}
