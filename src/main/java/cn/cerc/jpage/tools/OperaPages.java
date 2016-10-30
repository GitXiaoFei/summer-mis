package cn.cerc.jpage.tools;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.common.HtmlWriter;
import cn.cerc.jpage.common.TMutiPage;

public class OperaPages extends Component {
	private TMutiPage pages;
	private HttpServletRequest request;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public OperaPages(Component owner) {
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

		html.println("<section>");
		html.println("<div class=\"title\">数据分页</div>");
		html.println("<div class=\"contents\">");
		html.println("总记录数：%d, 当前页：%d，总页数：%d <br/>", pages.getRecordCount(), pages.getCurrent(), pages.getCount());
		html.println("<div align=\"center\">");
		html.println("<a href=\"?pageno=1%s\">首页</a>", url);
		html.println("<a href=\"?pageno=%d%s\">上一页</a>", pages.getPrior(), url);
		html.println("<a href=\"?pageno=%d%s\">下一页</a>", pages.getNext(), url);
		html.println("<a href=\"?pageno=%d%s\">尾页</a>", pages.getCount(), url);
		html.println("</div>");
		html.println("</div>");
		html.println("</section>");
	}

	public TMutiPage getPages() {
		return pages;
	}

	public OperaPages setPages(TMutiPage pages) {
		this.pages = pages;
		return this;
	}
}
