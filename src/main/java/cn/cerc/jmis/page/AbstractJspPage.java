package cn.cerc.jmis.page;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IPage;
import cn.cerc.jbean.other.MemoryBuffer;
import cn.cerc.jdb.other.utils;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlContent;
import cn.cerc.jpage.core.HtmlWriter;

public abstract class AbstractJspPage extends Component implements IPage {
	private String jspFile;
	private IForm form;
	private List<String> styleFiles = new ArrayList<>();
	private List<String> scriptFiles = new ArrayList<>();
	private List<HtmlContent> scriptCodes = new ArrayList<>();

	public AbstractJspPage() {
		super();
	}

	public AbstractJspPage(IForm form) {
		super();
		this.setForm(form);
	}

	@Override
	public final void setForm(IForm form) {
		this.form = form;
		if (form != null)
			this.add("jspPage", this);
	}

	@Override
	public final IForm getForm() {
		return form;
	}

	@Override
	public void addComponent(Component component) {
		if (component.getId() != null)
			this.add(component.getId(), component);
		super.addComponent(component);
	}

	public void execute() throws ServletException, IOException {
		String url = String.format("/WEB-INF/%s/%s", Application.getAppConfig().getPathForms(), this.getViewFile());
		getRequest().getServletContext().getRequestDispatcher(url).forward(getRequest(), getResponse());
	}

	public final String getJspFile() {
		return jspFile;
	}

	public final void setJspFile(String jspFile) {
		this.jspFile = jspFile;
	}

	public void add(String id, Object value) {
		getRequest().setAttribute(id, value);
	}

	public final String getMessage() {
		return form.getParam("message", null);
	}

	public final void setMessage(String message) {
		form.setParam("message", message);
	}

	public final String getViewFile() {
		String jspFile = this.getJspFile();
		if (getRequest() == null || jspFile == null)
			return jspFile;
		if (jspFile.indexOf(".jsp") == -1)
			return jspFile;

		if (this.getForm().getClient().isPhone())
			return jspFile;

		// 检查是否存在特定版本的jsp文件
		String rootPath = String.format("/WEB-INF/%s/", Application.getAppConfig().getPathForms());
		String fileName = jspFile.substring(0, jspFile.indexOf(".jsp"));
		String extName = jspFile.substring(jspFile.indexOf(".jsp") + 1);
		String newFile = String.format("%s-%s.%s", fileName, "pc", extName);
		if (fileExists(rootPath + newFile))
			return newFile;
		return jspFile;
	}

	protected boolean fileExists(String fileName) {
		URL url = AbstractJspPage.class.getClassLoader().getResource("");
		if (url == null)
			return false;
		String filepath = url.getPath();
		String appPath = filepath.substring(0, filepath.indexOf("/WEB-INF"));
		String file = appPath + fileName;
		File f = new File(file);
		return f.exists();
	}

	// 从请求或缓存读取数据
	public final String getValue(MemoryBuffer buff, String reqKey) {
		String result = getRequest().getParameter(reqKey);
		if (result == null) {
			String val = buff.getString(reqKey).replace("{}", "");
			if (utils.isNumeric(val) && val.endsWith(".0"))
				result = val.substring(0, val.length() - 2);
			else
				result = val;
		} else {
			result = result.trim();
			buff.setField(reqKey, result);
		}
		this.add(reqKey, result);
		return result;
	}

	public final List<String> getStyleFiles() {
		return styleFiles;
	}

	public final List<String> getScriptFiles() {
		return scriptFiles;
	}

	public final List<HtmlContent> getScriptCodes() {
		return scriptCodes;
	}

	public final void addStyleFile(String file) {
		styleFiles.add(file);
	}

	public final void addScriptFile(String scriptFile) {
		scriptFiles.add(scriptFile);
	}

	public final void addScriptCode(HtmlContent scriptCode) {
		scriptCodes.add(scriptCode);
	}

	// 返回所有的样式定义，供jsp中使用 ${jspPage.css}调用
	public final HtmlWriter getCss() {
		HtmlWriter html = new HtmlWriter();
		for (String file : styleFiles)
			html.println("<link href=\"%s\" rel=\"stylesheet\">", file);
		return html;
	}

	// 返回所有的脚本，供jsp中使用 ${jspPage.script}调用
	public final HtmlWriter getScript() {
		HtmlWriter html = new HtmlWriter();

		// 加入脚本文件
		for (String file : getScriptFiles()) {
			html.println("<script src=\"%s\"></script>", file);
		}
		// 加入脚本代码
		if (scriptCodes.size() > 0) {
			html.println("<script>");
			if (scriptCodes.size() > 0) {
				html.println("$(function(){");
				for (HtmlContent func : scriptCodes) {
					func.output(html);
				}
				html.println("});");
			}
			html.println("</script>");
		}
		return html;
	}

}
