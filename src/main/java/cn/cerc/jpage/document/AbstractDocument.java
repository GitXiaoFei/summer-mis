package cn.cerc.jpage.document;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jbean.form.IForm;
import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.common.HtmlWriter;
import cn.cerc.jpage.form.GoBackButton;
import cn.cerc.jpage.form.HeaderSide;
import cn.cerc.jpage.form.UrlMenu;
import cn.cerc.jpage.other.Url_Record;

public abstract class AbstractDocument extends Component {
	private HeaderSide header;
	private HttpServletRequest request;
	private List<String> styleFiles = new ArrayList<>();
	private List<String> scriptFiles = new ArrayList<>();
	private List<HtmlContent> codes1 = new ArrayList<>();
	private List<HtmlContent> codes2 = new ArrayList<>();
	private List<HtmlContent> contents = new ArrayList<>();

	public AbstractDocument(IForm form, Component owner) {
		super();
		this.setId("document");
		this.setOwner(owner);
		this.request = form.getRequest();
	}

	public HtmlWriter getScript() {
		HtmlWriter html = new HtmlWriter();
		html.println("<script src=\"js/jquery-1.11.1.min.js\"></script>");
		html.println("<script src=\"js/delphi.vcl.js\"></script>");
		html.println("<script src=\"js/TApplication.js\"></script>");
		html.println("<script src=\"js/dialog.js\"></script>");
		html.println("<script src=\"js/Shopping.js\"></script>");

		// 加入脚本文件
		for (String file : scriptFiles) {
			html.println("<script src=\"%s\"></script>", file);
		}
		// 加入脚本代码
		if (codes1.size() > 0 || codes2.size() > 0) {
			html.println("<script>");
			for (HtmlContent func : codes1) {
				func.output(html);
			}
			if (codes2.size() > 0) {
				html.println("$(function(){");
				for (HtmlContent func : codes2) {
					func.output(html);
				}
				html.println("});");
			}
			html.println("</script>");
		}
		return html;
	}

	/*
	 * 注册到jsp文件中
	 */

	public void register() {
		Boolean _showMenu_ = (Boolean) request.getAttribute("_showMenu_");
		if (_showMenu_ != null && _showMenu_) {
			header = new HeaderSide();

			Component left = header.getLeft();
			@SuppressWarnings("unchecked")
			List<Url_Record> barMenus = (List<Url_Record>) request.getAttribute("barMenus");
			if (barMenus == null) {
				new UrlMenu(left, "首页", "/");
				new UrlMenu(left, "刷新", "javascript:history.go(-1);");
				new GoBackButton(left);
			} else {
				new GoBackButton(left);
				for (Url_Record menu : barMenus) {
					new UrlMenu(left, menu.getCaption(), menu.getUrl());
				}
			}

			@SuppressWarnings("unchecked")
			List<Url_Record> mainMenus = (List<Url_Record>) request.getAttribute("mainMenus");
			if (mainMenus != null) {
				for (Url_Record menu : mainMenus) {
					new UrlMenu(left, menu.getCaption(), menu.getUrl());
				}
			}

			Component right = header.getRight();
			@SuppressWarnings("unchecked")
			List<Url_Record> subMenus = (List<Url_Record>) request.getAttribute("subMenus");
			int i = subMenus.size() - 1;
			while (i > -1) {
				Url_Record menu = subMenus.get(i);
				new UrlMenu(right, menu.getCaption(), menu.getUrl());
				i--;
			}
		}

		request.setAttribute(this.getId(), this);
		for (Component component : getComponents()) {
			request.setAttribute(component.getId(), component);
		}
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public abstract String getViewFile();

	public String getHeader() {
		return header != null ? header.getHtml() : null;
	}

	public String getCss() {
		HtmlWriter html = new HtmlWriter();
		for (String file : styleFiles) {
			html.println("<link href=\"%s\" rel=\"stylesheet\">", file);
		}
		return html.toString();
	}

	public void addCSSFile(String file) {
		styleFiles.add(file);
	}

	public void appendContent(HtmlContent content) {
		contents.add(content);
	}

	public void addDefineScript(HtmlContent scriptCode) {
		codes1.add(scriptCode);
	}

	public void addScriptCode(HtmlContent scriptCode) {
		codes2.add(scriptCode);
	}

	public String getContents() {
		if (contents.size() == 0)
			return "";
		HtmlWriter html = new HtmlWriter();
		for (HtmlContent content : contents) {
			content.output(html);
		}
		return html.toString();
	}

	public void addScriptFile(String scriptFile) {
		scriptFiles.add(scriptFile);
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
