package cn.cerc.jmis.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.core.IPassport;
import cn.cerc.jbean.form.IMenu;
import cn.cerc.jbean.other.BookOptions;
import cn.cerc.jbean.other.SystemTable;
import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.IHandle;
import cn.cerc.jdb.mysql.SqlQuery;

public class MenuFactory implements IAppMenus {
	// private static final Logger log = Logger.getLogger(MenuFactory.class);

	private static final String menuFile = "app-menus.xml";
	private static final Map<String, MenuData> menus = new LinkedHashMap<>();
	private DataSet cusMenus;
	private IHandle handle;
	private IPassport passport;

	public static final String TAcc = "TAcc";

	static {
		try {
			menus.clear();
			String filepath = MenuFactory.class.getClassLoader().getResource("").toURI().getPath() + menuFile;
			File f = new File(filepath);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);

			Element body = doc.getRootElement().element("body");
			if (body == null)
				throw new RuntimeException(menuFile + " 格式不正确！");

			for (Iterator<?> j = body.elementIterator("item"); j.hasNext();) {
				Element item = (Element) j.next();

				if (!"true".equals(item.attributeValue("delete"))) {
					MenuData menuItem = new MenuData();
					menuItem.setId(item.attributeValue("code"));
					menuItem.setCaption(item.attributeValue("name"));
					if (item.attributeValue("security") != null)
						menuItem.setSecurity("true".equals(item.attributeValue("security")));
					else
						menuItem.setSecurity(true);
					menuItem.setHide("true".equals(item.attributeValue("hide")));
					menuItem.setFolder("true".equals(item.attributeValue("folder")));
					menuItem.setCustom("true".equals(item.attributeValue("custom")));
					menuItem.setProccode(item.attributeValue("proccode"));
					menuItem.setParent(menuItem.getHide() ? "hidden" : item.attributeValue("parent"));

					Element remark = item.element("remark");
					menuItem.setDescribe(remark != null ? remark.getText() : "");

					menuItem.setWin(true);
					Element web = item.element("web");
					if (web != null) {
						menuItem.setClazz(web.attributeValue("class"));
						menuItem.setFormNo(web.attributeValue("pageno"));
						menuItem.setImage(web.attributeValue("image"));
						menuItem.setProcess(web.attributeValue("process"));
						menuItem.setWeb(!"none".equals(menuItem.getProcess()));
						menuItem.setWin(!"release".equals(menuItem.getProcess()));
					}

					Element access = item.element("access");
					menuItem.setVersions(access.attributeValue("verlist"));
					menus.put(menuItem.getId(), menuItem);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, MenuData> getItems() {
		return menus;
	}

	public static MenuData get(String beanID) {
		return menus.get(beanID);
	}

	public IMenu getItem(String menuId) {
		MenuData item = get(menuId);
		if (item == null)
			throw new RuntimeException(String.format("menu %s not find!", menuId));

		MenuItem menu = new MenuItem();
		menu.setId(menuId);
		copyMenuData(item, menu);
		return menu;
	}

	@Override
	public List<MenuItem> getList(IHandle handle, String parentMenu, boolean security) {
		this.handle = handle;
		if (this.passport == null)
			this.passport = Application.getPassport(handle);
		
		Map<String, MenuData> menus = MenuFactory.getItems();
		List<MenuItem> result = new ArrayList<MenuItem>();
		// 初筛出符合要求的菜单项
		for (String menuId : menus.keySet()) {
			MenuData item = menus.get(menuId);
			if (passItem(item, parentMenu, security)) {
				MenuItem menu = new MenuItem();
				menu.setId(menuId);
				copyMenuData(item, menu);
				result.add(menu);
			}
		}
		return result;
	}

	protected boolean passItem(MenuData item, String parentId, boolean security) {
		// 不返回首页
		if (item.getId().equals(Application.getConfig().getFormDefault()))
			return false;

		// 不返回隐藏菜单
		if (item.getHide())
			return false;

		// 是否是客制菜单
		if (item.getCustom()) {
			if (!this.getCusMenus(handle).locate("Code_", item.getId()))
				return false;
		}

		if (!parentId.equals(item.getParent()))
			return false;

		// 当前用户是否拥有权限
		if (item.isSecurity() != security)
			return false;

		// 进行权限检查
		if (!passport.passProc(item.getVersions(), item.getProccode()))
			return false;

		if (item.getId().equals(TAcc)) {
			if (!BookOptions.isEnableAccBook(handle))
				return false;
		}
		return true;
	}

	protected DataSet getCusMenus(IHandle handle) {
		if (cusMenus == null) {
			SqlQuery ds = new SqlQuery(handle);
			ds.add("select Code_ from %s", SystemTable.get(SystemTable.getCustomMenus));
			ds.add("where CorpNo_='%s'", handle.getCorpNo());
			ds.open();
			this.cusMenus = ds;
		}
		return cusMenus;
	}

	private void copyMenuData(MenuData item, MenuItem menu) {
		menu.setParam(MenuItem.TITLE, item.getCaption());
		menu.setParam(MenuItem.PAGENO, item.getFormNo());
		menu.setParam(MenuItem.SECURITY, item.isSecurity() ? "true" : "false");
		menu.setParam(MenuItem.SOFTWARE, item.getVersions());
		menu.setParam(MenuItem.PERMISSION, item.getProccode());
		menu.setParam(MenuItem.PARENT, item.getParent());
		menu.setParam(MenuItem.IMAGE, item.getImage());
	}

}
