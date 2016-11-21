package cn.cerc.jpage.tools;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jbean.form.IForm;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.grid.MutiPage;

public class OperaPages extends Component {
	private IForm form;
	private MutiPage pages;

	public OperaPages(IForm form, MutiPage pages) {
		this.form = form;
		this.pages = pages;
	}

	@Override
	public void output(HtmlWriter html) {
		if (pages.getCount() <= 1)
			return;

		HttpServletRequest request = form.getRequest();
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
		boolean isPhone = form.getClient().isPhone();
		if (isPhone) {
			html.println("<div class=\"foot-page\">");
		} else {
			html.println("<section>");
			html.println("<div class=\"title\">数据分页</div>");
			html.println("<div class=\"contents\">");
			html.println("总记录数：%d, 当前页：%d，总页数：%d <br/>", pages.getRecordCount(), pages.getCurrent(), pages.getCount());
			html.println("<div align=\"center\">");
		}
		html.println("<a href=\"?pageno=1%s\">首页</a>", url);
		html.println("<a href=\"?pageno=%d%s\">上一页</a>", pages.getPrior(), url);
		html.println("<a href=\"?pageno=%d%s\">下一页</a>", pages.getNext(), url);
		html.println("<a href=\"?pageno=%d%s\">尾页</a>", pages.getCount(), url);
		if (isPhone) {
			html.println("笔数：%s, 页数：%d / %d", pages.getRecordCount(), pages.getCurrent(), pages.getCount());
			html.println("</div>");
		} else {
			html.println("</div>");
			html.println("</div>");
			html.println("</section>");
		}
	}
}
