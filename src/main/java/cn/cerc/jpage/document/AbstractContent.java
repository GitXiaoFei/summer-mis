package cn.cerc.jpage.document;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jmis.page.AbstractJspPage;
import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.common.HtmlWriter;
import cn.cerc.jpage.form.GoBackButton;
import cn.cerc.jpage.form.HeaderSide;
import cn.cerc.jpage.form.UrlMenu;
import cn.cerc.jpage.other.Url_Record;

public abstract class AbstractContent extends Component {
	private HeaderSide header;
	private List<HtmlContent> codes1 = new ArrayList<>();
	private List<HtmlContent> contents = new ArrayList<>();
	private AbstractJspPage page;

	public AbstractContent(AbstractJspPage owner) {
		super(owner);
		this.page = owner;
		owner.setDocument(this);
		this.init();
	}

	// 进行各类初始化
	public abstract void init();

	public HtmlWriter getScript() {
		HtmlWriter html = new HtmlWriter();

		// 加入脚本文件
		for (String file : this.page.getScriptFiles()) {
			html.println("<script src=\"%s\"></script>", file);
		}
		// 加入脚本代码
		List<HtmlContent> scriptCodes = this.getPage().getScriptCodes();
		if (codes1.size() > 0 || scriptCodes.size() > 0) {
			html.println("<script>");
			for (HtmlContent func : codes1) {
				func.output(html);
			}
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

	/*
	 * 注册到jsp文件中
	 */

	public void register() {
		HttpServletRequest request = page.getRequest();
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

	public String getHeader() {
		return header != null ? header.getHtml() : null;
	}

	@Deprecated
	public HtmlWriter getCss() {
		return page.getCss();
	}

	public void appendContent(HtmlContent content) {
		contents.add(content);
	}

	public void addDefineScript(HtmlContent scriptCode) {
		codes1.add(scriptCode);
	}

	public HtmlWriter getContents() {
		HtmlWriter html = new HtmlWriter();
		if (contents.size() == 0)
			return html;
		for (HtmlContent content : contents) 
			content.output(html);
		return html;
	}

	public AbstractJspPage getPage() {
		return page;
	}
}
