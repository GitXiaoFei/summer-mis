package cn.cerc.jmis.core;

import java.util.HashMap;
import java.util.Map;

import cn.cerc.jbean.form.IMenu;

public class MenuItem implements IMenu {
	// 菜单代码
	private String id;
	// 菜单标题
	public static final String TITLE = "title";
	// 菜单编号，一般为数字
	public static final String PAGENO = "formNo";
	// 软件类别，如 1,2,，其中1及2各代表一种软件
	public static final String SOFTWARE = "versions";
	// 菜单授权码
	public static final String PERMISSION = "procCode";
	// true: 需要登录方可使用
	public static final String SECURITY = "security";
	// 上级菜单，若无，则为""
	public static final String PARENT = "parentId";
	// 菜单图标，为URL值
	public static final String IMAGE = "image";
	// 所有参数值
	private Map<String, String> params = new HashMap<>();

	public MenuItem() {

	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getTitle() {
		String result = this.getParam(TITLE);
		return result != null ? result : "";
	}

	@Override
	public String getPageNo() {
		String result = this.getParam(PAGENO);
		return result != null ? result : "";
	}

	@Override
	public boolean isSecurityEnabled() {
		String result = this.getParam(SECURITY);
		return result != null ? "true".equals(result) : true;
	}

	@Override
	public String getPermissionCode() {
		String result = this.getParam(PERMISSION);
		return result != null ? result : "";
	}

	@Override
	public String getSoftwareList() {
		String result = this.getParam(SOFTWARE);
		return result != null ? result : "";
	}

	@Override
	public String getParentId() {
		String result = this.getParam(PARENT);
		return result != null ? result : "";
	}

	@Override
	public String getImage() {
		String result = this.getParam(IMAGE);
		return result != null ? result : "";
	}

	@Override
	public void setParam(String key, String value) {
		params.put(key, value);
	}

	@Override
	public String getParam(String key) {
		return params.get(key);
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

}
