package cn.cerc.jmis.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.other.BookOptions;
import cn.cerc.jbean.other.BookVersion;
import cn.cerc.jbean.other.SystemTable;
import cn.cerc.jdb.mysql.SqlQuery;
import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.IHandle;
import cn.cerc.jmis.core.MenuFactory;
import cn.cerc.jmis.core.MenuData;

public class SystemMenu {
	// private static final Logger log = Logger.getLogger(SystemMenu.class);
	public static final String TAcc = "TAcc";

	private IHandle handle;
	private DataSet cusMenus;
	private BookVersion corpType = null;

	public SystemMenu(IHandle sess) {
		this.handle = sess;
	}

	public List<MenuData> getList(String parentMenu, boolean security, String device) {
		Map<String, MenuData> menus = MenuFactory.getItems();
		List<MenuData> result = new ArrayList<MenuData>();
		// 初筛出符合要求的菜单项
		for (String key : menus.keySet()) {
			MenuData item = menus.get(key);
			if (passItem(item, parentMenu, security, device)) {
				result.add(item);
			}
		}
		return result;
	}

	protected boolean passItem(MenuData item, String parentMenu, boolean security, String device) {
		// 不返回首页
		if (item.getId().equals(Application.getConfig().getFormDefault()))
			return false;

		// 不返回隐藏菜单
		if (item.getHide())
			return false;

		// 是否是客制菜单
		if (item.getCustom()) {
			if (!this.getCusMenus().locate("Code_", item.getId()))
				return false;
		}

		if (!parentMenu.equals(item.getParent()))
			return false;

		// 当前用户是否拥有权限
		if (item.isSecurity() != security)
			return false;

		// 进行权限检查
		if (!Application.getPassport(handle).passProc(item.getVersions(), item.getProccode()))
			return false;

		if (item.getId().equals(TAcc)) {
			if (!BookOptions.isEnableAccBook(handle))
				return false;
		}
		return true;
	}

	protected DataSet getCusMenus() {
		if (cusMenus == null) {
			SqlQuery ds = new SqlQuery(handle);
			ds.add("select Code_ from %s", SystemTable.get(SystemTable.getCustomMenus));
			ds.add("where CorpNo_='%s'", handle.getCorpNo());
			ds.open();
			this.cusMenus = ds;
		}
		return cusMenus;
	}

	protected void setCusMenus(DataSet cusMenus) {
		this.cusMenus = cusMenus;
	}

	public BookVersion getCorpType() {
		return corpType;
	}

	public void setCorpType(BookVersion corpType) {
		this.corpType = corpType;
	}
}
