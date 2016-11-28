package cn.cerc.jmis.page;

import static cn.cerc.jmis.core.ClientDevice.device_ee;
import static cn.cerc.jmis.core.ClientDevice.device_pad;
import static cn.cerc.jmis.core.ClientDevice.device_pc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IJspPage;
import cn.cerc.jbean.form.IMainForm;
import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.common.TMutiPage;
import cn.cerc.jpage.document.CustomDocument;
import cn.cerc.jpage.grid.Grid;
import cn.cerc.jpage.other.DBGrid;
import cn.cerc.jpage.tools.OperaPages;
import cn.cerc.jpage.tools.PhonePages;

public class JspChildPage extends Component implements IJspPage {
	private String file;
	protected IForm form;
	private CustomDocument document;

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

	@Override
	public IJspPage getPage() {
		return new JspPage(this.form, getViewFile());
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
		if (component instanceof CustomDocument) {
			this.document = (CustomDocument) component;
			setFile(document.getViewFile());
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

	public CustomDocument getDocument() {
		return document;
	}

	public void setDocument(CustomDocument document) {
		this.document = document;
	}

	@Override
	public void execute() throws ServletException, IOException {
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		if ("system/document.jsp".equals(this.getPage())) {
			IMainForm obj = Application.getMainPage();
			if (obj != null)
				obj.execute(form);
			PrintWriter out = response.getWriter();
			CustomDocument doc = (CustomDocument) request.getAttribute("document");
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.printf("<title>%s</title>\n", form.getTitle());
			out.printf("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n");
			out.printf("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n");
			out.printf("<link href=\"css/style-phone.css\" rel=\"stylesheet\">\n");
			if (!form.getClient().isPhone())
				out.printf("<link href=\"css/style-pc.css\" rel=\"stylesheet\">\n");
			out.print(doc.getCss());
			out.print(doc.getScript());
			out.println("<script>");
			out.println("var Application = new TApplication();");
			out.printf("Application.device = '%s';\n", getAttribute("device", ""));

			Component bottom = (Component) request.getAttribute("bottom");
			if (bottom != null)
				out.printf("Application.bottom = '%s';\n", bottom.getId());
			out.printf("Application.message = '%s';\n", getAttribute("msg", ""));
			out.printf("Application.searchFormId = '%s';\n", getAttribute("searchFormId", ""));

			out.printf("session().set('childForm', '%s');\n", getAttribute("scriptFile", ""));
			out.println("$(document).ready(function() {");
			out.println("Application.init();");
			out.println("if (Application.onready)");
			out.println("	Application.onready();");
			out.println("if (!localStorage) {");
			out.println("	$('.main').attr('display', 'block');");
			out.println("}");
			out.println("head_main();");
			out.println("});");
			out.println("</script>");
			out.println("</head>");
			out.println("<body>");
			out.println(getAttribute("_showAd_", ""));
			out.println(doc.getHeader());

			out.write("<div class=\"main\">\n");
			if (bottom != null)
				out.write("<div class=\"info-newStyle\">\n");

			if (!form.getClient().isPhone()) {
				if (request.getAttribute("bottom") == null)
					out.println("<div id='msg'></div>");
			} else {
				out.println("<div id='msg'></div>");
				out.println("<span id='back-top' style='display: none'>顶部</span>");
				out.println("<span id='back-bottom' style='display: none'>底部</span>");
			}
			out.println("<div class='leftSide'>");
			out.print(getAttribute("search", ""));
			Grid grid = (Grid) request.getAttribute("grid");
			if (grid != null) {
				if (!form.getClient().isPhone()) {
					if (grid.isExtGrid()) {
						out.print(grid);
					} else {
						out.printf("<div class='scrollArea'>%s</div>", grid);
					}
				} else
					out.printf("<div class='scrollArea'>%s</div>", grid);
			}
			out.println("</div>");

			out.println("<div class='rightSide'>");
			out.print(getAttribute("rightSide", ""));
			out.print(getAttribute("_operaPages_", ""));
			out.println("</div>");

			if (bottom == null)
				out.print(getAttribute("_operaPages_", ""));
			else {
				out.print(bottom);
				out.println("</div>");
			}
			out.println("</div>\n");
			out.println("<div class='bottom-space'></div>");
			out.print(doc.getContents());
			out.println("</body>");
			out.println("</html>");
		} else {
			JspPage output = new JspPage(form);
			output.setFile(this.getViewFile());
			output.execute();
		}
	}

	private Object getAttribute(String Id, String def) {
		HttpServletRequest request = getRequest();
		Object result = request.getAttribute(Id);
		if (result == null)
			return def;
		return result;
	}
}
