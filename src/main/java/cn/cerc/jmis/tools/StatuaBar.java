package cn.cerc.jmis.tools;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IPage;
import cn.cerc.jmis.core.ClientDevice;
import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.common.HtmlWriter;
import cn.cerc.jpage.form.UrlMenu;
import cn.cerc.jpage.other.Url_Record;

public class StatuaBar extends Component {
	private IForm form;
	private static final int MAX_MENUS = 6;
	protected Url_Record checkAll;

	public StatuaBar(IPage owner) {
		super((Component) owner);
		this.form = owner.getForm();
		this.setId("bottom");
	}

	public void addMenu(String caption, String url) {
		int count = 1;
		for (Component obj : this.getComponents()) {
			if (obj instanceof UrlMenu) {
				count++;
			}
		}
		UrlMenu item = new UrlMenu(this, caption, url);
		item.setCssClass("bottomBotton");
		item.setId("button" + count);
		ClientDevice info = new ClientDevice(form);
		info.setRequest(form.getRequest());
		if (!info.isPhone())
			item.setName(String.format("F%s:%s", count, item.getName()));
	}

	public Url_Record getCheckAll() {
		return checkAll;
	}

	public void enableCheckAll(String targetId) {
		if (targetId == null || "".equals(targetId))
			throw new RuntimeException("targetId is null");
		if (checkAll != null)
			throw new RuntimeException("checkAll is not null");
		checkAll = new Url_Record(String.format("selectItems('%s')", targetId), "全选");
	}

	@Override
	public void output(HtmlWriter html) {
		if (this.getComponents().size() > MAX_MENUS)
			throw new RuntimeException(String.format("底部菜单区最多只支持 %d 个菜单项", MAX_MENUS));
		html.println("<div class=\"operaBottom\">");
		if (this.checkAll != null) {
			html.print("<input type=\"checkbox\"");
			html.print(" id=\"selectAll\"");
			html.print(" onclick=\"%s\"/>", checkAll.getUrl());
			html.println("<label for=\"selectAll\">全选</label>");
		}
		super.output(html);
		HttpServletRequest request = getForm().getRequest();
		if (request != null) {
			ClientDevice info = new ClientDevice(form);
			info.setRequest(request);
			if (!info.isPhone()) {
				String msg = request.getParameter("msg");
				html.print("<div class=\"bottom-message\"");
				html.print(" id=\"msg\">");
				if (msg != null)
					html.print(msg.replaceAll("\r\n", "<br/>"));
				html.println("</div>");
			}
		}
		html.println("</div>");
	}

	public IForm getForm() {
		return form;
	}
}
