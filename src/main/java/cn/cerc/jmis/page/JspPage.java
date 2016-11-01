package cn.cerc.jmis.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.core.CustomHandle;
import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IJspPage;
import cn.cerc.jmis.form.AbstractForm;
import cn.cerc.jmis.form.MainMenu;
import cn.cerc.jmis.form.RightMenus;
import cn.cerc.jpage.other.Url_Record;

public class JspPage extends AbstractPage implements IJspPage {
	private String jspFile;

	public JspPage(IForm form) {
		super(form);
	}

	public JspPage(IForm form, String jspFile) {
		super(form);
		this.setFile(jspFile);
	}

	public void execute() throws ServletException, IOException {
//		HttpServletRequest request = form.getRequest();
//		CustomHandle sess = (CustomHandle) form.getHandle().getProperty(null);
//		request.setAttribute("passport", sess.logon());
//		request.setAttribute("logon", sess.logon());
//		MainMenu mainMenu = ((AbstractForm) form).getMainMenu();
//		if (sess.logon()) {
//			List<Url_Record> rightMenus = mainMenu.getRightMenus();
//			RightMenus menus = Application.getBean("RightMenus", RightMenus.class);
//			menus.setHandle(form.getHandle());
//			for (IMenuBar item : menus.getItems())
//				item.enrollMenu(form.getHandle(), request, rightMenus);
//		} else {
//			mainMenu.getHomePage().setUrl(Application.getConfig().getFormWelcome());
//		}
//		// 设置首页
//		request.setAttribute("_showMenu_", "true".equals(form.getParam("showMenus", "true")));
//		// 系统通知消息
//		if (request.getAttribute("message") == null)
//			request.setAttribute("message", "");
//
//		String msg = form.getParam("message", "");
//		request.setAttribute("msg", msg == null ? "" : msg.replaceAll("\r\n", "<br/>"));
//		request.setAttribute("formno", form.getParam("formNo", "000"));
//		request.setAttribute("form", form);
		
		String url = String.format("/WEB-INF/%s/%s", Application.getConfig().getPathForms(), jspFile);
		getRequest().getServletContext().getRequestDispatcher(url).forward(getRequest(), getResponse());
	}

	public String getJspFile() {
		return jspFile;
	}

	@Override
	public void setFile(String jspFile) {
		this.jspFile = jspFile;
	}

	@Override
	public void add(String id, Object value) {
		getRequest().setAttribute(id, value);
	}

	@Override
	public String getMessage() {
		return form.getParam("message", null);
	}

	@Override
	public void setMessage(String message) {
		form.setParam("message", message);
	}

	@Override
	public String getViewFile() {
		return jspFile;
	}

}
