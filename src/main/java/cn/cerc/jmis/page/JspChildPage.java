package cn.cerc.jmis.page;

import static cn.cerc.jmis.core.ClientDevice.device_ee;
import static cn.cerc.jmis.core.ClientDevice.device_pad;
import static cn.cerc.jmis.core.ClientDevice.device_pc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.common.TMutiPage;
import cn.cerc.jpage.document.AbstractContent;
import cn.cerc.jpage.grid.Grid;
import cn.cerc.jpage.other.DBGrid;
import cn.cerc.jpage.other.Url_Record;
import cn.cerc.jpage.tools.OperaPages;
import cn.cerc.jpage.tools.PhonePages;

public class JspChildPage extends Component implements IJspPage {
	private String file;
	protected IForm form;
	private AbstractContent content;
	private MainMenu mainMenu = new MainMenu();

	public JspChildPage(IForm form) {
		super();
		this.setForm(form);
	}

	public String getMessage() {
		return form.getParam("message", null);
	}

	public void setMessage(String message) {
		form.setParam("message", message);
	}

	public String getFile() {
		return file;
	}

	@Override
	public void setFile(String jspFile) {
		this.file = jspFile;
	}

	public String getViewFile() {
		if (getRequest() == null || file == null)
			return file;
		if (file.indexOf(".jsp") == -1)
			return file;

		String device = form.getClient().getDevice();

		String rootPath = String.format("/WEB-INF/%s/", Application.getConfig().getPathForms());
		String fileName = file.substring(0, file.indexOf(".jsp"));
		String extName = file.substring(file.indexOf(".jsp") + 1);

		if (device_pc.equals(device) || device_ee.equals(device) || device_pad.equals(device)) {
			String filePath = String.format("%s-%s.%s", fileName, "pc", extName);
			if (fileExists(rootPath + filePath))
				return filePath;
		}
		return file;
	}

	private boolean fileExists(String fileName) {
		URL url = JspChildPage.class.getClassLoader().getResource("");
		if (url == null)
			return false;
		String filepath = url.getPath();
		String appPath = filepath.substring(0, filepath.indexOf("/WEB-INF"));
		String file = appPath + fileName;
		File f = new File(file);
		return f.exists();
	}

	public void addExportFile(String service, String key) {
		if (device_ee.equals(form.getClient().getDevice())) {
			ExportFile item = new ExportFile(service, key);
			this.add("export", item);
		}
	}

	@Override
	public void addComponent(Component component) {
		if (component instanceof AbstractContent) {
			this.content = (AbstractContent) component;
		}
		if (component.getId() != null)
			this.add(component.getId(), component);
		super.addComponent(component);
	}

	public IForm getForm() {
		return form;
	}

	@Override
	public void setForm(IForm form) {
		this.form = form;
	}

	@Override
	public void add(String id, Object value) {
		HttpServletRequest request = getRequest();
		TMutiPage pages = null;
		if (value instanceof Grid) {
			Grid grid = (Grid) value;
			request.setAttribute(id, value);
			pages = grid.getPages();
		} else if (value instanceof DBGrid) {
			DBGrid<?> grid = (DBGrid<?>) value;
			request.setAttribute(id, grid.getList());
			pages = grid.getPages();
		} else
			request.setAttribute(id, value);

		// 添加分页控制
		if (pages != null) {
			if (form.getClient().isPhone())
				new PhonePages(this).setPages(pages).setRequest(request);
			else
				new OperaPages(this).setPages(pages).setRequest(request);
			request.setAttribute("pages", pages);
		}
	}

	public AbstractContent getContent() {
		return content;
	}

	public void setContent(AbstractContent document) {
		this.content = document;
	}

	@Override
	public void execute() throws ServletException, IOException {
		ready(this, form);
		JspPage output = new JspPage(form);
		output.setFile(this.getViewFile());
		output.execute();
	}

	public IJspPage getPage() {
		ready(this, form);
		return new JspPage(this.form, getViewFile());
	}

	private void ready(IJspPage page, IForm form) {
		HttpServletRequest request = form.getRequest();
		CustomHandle sess = (CustomHandle) form.getHandle().getProperty(null);
		request.setAttribute("passport", sess.logon());
		request.setAttribute("logon", sess.logon());
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
			if (page instanceof JspChildPage) {
				JspChildPage obj = (JspChildPage) page;
				String device = form.getClient().getDevice();
				mainMenu.finish(obj, sess.logon(), device);
				if (obj.getContent() != null)
					obj.getContent().register();
			}
		}
		String msg = form.getParam("message", "");
		request.setAttribute("msg", msg == null ? "" : msg.replaceAll("\r\n", "<br/>"));
		request.setAttribute("formno", form.getParam("formNo", "000"));
		request.setAttribute("form", form);
	}

	public MainMenu getMainMenu() {
		return mainMenu;
	}
}
