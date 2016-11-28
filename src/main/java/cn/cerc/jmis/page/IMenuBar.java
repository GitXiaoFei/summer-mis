package cn.cerc.jmis.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jdb.core.IHandle;
import cn.cerc.jpage.other.Url_Record;

public interface IMenuBar {
	// 登记菜单栏菜单项
	public int enrollMenu(IHandle handle, HttpServletRequest request, List<Url_Record> menus);
}
