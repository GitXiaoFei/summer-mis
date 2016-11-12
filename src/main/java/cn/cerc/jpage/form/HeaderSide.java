package cn.cerc.jpage.form;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class HeaderSide extends Component {
	private Component left = new Component();
	private Component right = new Component();

	public Component getLeft() {
		return left;
	}

	public Component getRight() {
		return right;
	}

	public void output(HtmlWriter html) {
		html.println("<header>");
		html.println("<nav class=\"navigation\">");
		int i = 0;
		html.println("<div class=\"menu\">");
		for (Component item : left.getComponents()) {
			if (i > 2)
				html.println("<a style=\"padding: 0.5em 0\">→</a>");
			item.output(html);
			i++;
		}
		html.println("</div>");
		html.println("<div class=\"menu\" style=\"float: right;\">");
		for (Component item : right.getComponents()) {
			item.output(html);
		}
		html.println("</div>");
		html.println("</nav>");
		html.println("</header>");
	}
}
