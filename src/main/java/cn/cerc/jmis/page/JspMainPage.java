package cn.cerc.jmis.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jbean.core.CustomHandle;
import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.core.AbstractHandle;
import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IJspPage;
import cn.cerc.jbean.form.IMainForm;
import cn.cerc.jmis.form.AbstractForm;
import cn.cerc.jmis.form.MainMenu;
import cn.cerc.jmis.form.RightMenus;
import cn.cerc.jpage.other.Url_Record;

public class JspMainPage extends AbstractHandle implements IMainForm {
	@Override
	public void execute(IForm form) {
		HttpServletRequest request = form.getRequest();
		CustomHandle sess = (CustomHandle) form.getHandle().getProperty(null);
		request.setAttribute("passport", sess.logon());
		request.setAttribute("logon", sess.logon());
		MainMenu mainMenu = ((AbstractForm) form).getMainMenu();
		if (sess.logon()) {
			List<Url_Record> rightMenus = mainMenu.getRightMenus();
			RightMenus menus = Application.getBean("RightMenus", RightMenus.class);
			menus.setHandle(form.getHandle());
			for (IMenuBar item : menus.getItems())
				item.enrollMenu(form.getHandle(), request, rightMenus);
		} else {
			mainMenu.getHomePage().setUrl(Application.getConfig().getFormWelcome());
		}
		// 设置首页
		request.setAttribute("_showMenu_", "true".equals(form.getParam("showMenus", "true")));
		// 系统通知消息
		if (request.getAttribute("message") == null)
			request.setAttribute("message", "");

		if (form instanceof AbstractForm) {
			IJspPage view = ((AbstractForm) form).getJspView();
			if (view instanceof JspChildPage) {
				JspChildPage obj = (JspChildPage) view;
				String device = form.getClient().getDevice();
				((AbstractForm) form).getMainMenu().finish(obj, sess.logon(), device);
				if (obj.getDocument() != null)
					obj.getDocument().register();
			}
		}
		String msg = form.getParam("message", "");
		request.setAttribute("msg", msg == null ? "" : msg.replaceAll("\r\n", "<br/>"));
		request.setAttribute("formno", form.getParam("formNo", "000"));
		request.setAttribute("form", form);
	}
}
