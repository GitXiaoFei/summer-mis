package cn.cerc.jmis.core;

import java.util.List;

import cn.cerc.jbean.form.IMenu;
import cn.cerc.jdb.core.IHandle;

public interface IAppMenus {

	// 返回系统菜单定义
	public IMenu getItem(String menuId);

	// 返回指定父菜单下的所有子菜单
	public List<MenuItem> getList(IHandle handle, String parentId, boolean security);
}
