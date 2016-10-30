package cn.cerc.jpage.tools;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.common.HtmlWriter;
import cn.cerc.jpage.common.TMutiPage;

public class PhonePages extends Component {
	private TMutiPage pages;
	private HttpServletRequest request;

	public PhonePages(Component owner) {
		super(owner);
		this.setId("_operaPages_");
	}

	@Override
	public void output(HtmlWriter html) {
		if (pages.getCount() <= 1)
			return;

		StringBuffer url = new StringBuffer();
		if (request.getQueryString() != null) {
			String[] items = request.getQueryString().split("&");
			for (String str : items) {
				if (!str.startsWith("pageno=")) {
					url.append("&");
					url.append(str);
				}
			}
		}

		html.println("<div class=\"foot-page\">");
		html.println("<a href=\"?pageno=1%s\">首页</a>", url);
		html.println("<a href=\"?pageno=%d%s\">上一页</a>", pages.getPrior(), url);
		html.println("<a href=\"?pageno=%d%s\">下一页</a>", pages.getNext(), url);
		html.println("<a href=\"?pageno=%d%s\">尾页</a>", pages.getCount(), url);
		html.println("笔数：%s, 页数：%d / %d", pages.getRecordCount(), pages.getCurrent(), pages.getCount());
		html.println("</div>");
	}

	public TMutiPage getPages() {
		return pages;
	}

	public PhonePages setPages(TMutiPage pages) {
		this.pages = pages;
		return this;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
