package cn.cerc.jpage.grid;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class PhoneGrid extends Grid {
	// 手机专用行
	private List<PhoneLine> lines = new ArrayList<>();

	public PhoneGrid() {
		super();
	}

	public PhoneGrid(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<div class='scrollArea'>");
		if (this.getDataSet().size() > 0) {
			if (form != null) {
				form.outHead(html);
				outputGrid(html);
				form.outFoot(html);
			} else {
				outputGrid(html);
			}
		}
		html.print("</div>");
	}

	@Override
	public void outputGrid(HtmlWriter html) {
		DataSet dataSet = this.getDataSet();
		MutiPage pages = this.getPages();
		if (dataSet.size() == 0)
			return;

		html.println(String.format("<ol class=\"%s\">", this.getCSSClass_Phone()));

		int i = pages.getBegin();
		while (i <= pages.getEnd()) {
			dataSet.setRecNo(i + 1);
			int flag = 0;
			html.println("<li>");
			for (PhoneLine line : this.lines) {
				if (line.isTable()) {
					if (flag == 0) {
						html.println("<table>");
						flag = 1;
					} else if (flag == 2) {
						html.println("</table>");
						html.println("<table>");
					}
				} else {
					if (flag == 1) {
						html.println("</table>");
						flag = 2;
					}
				}
				line.output(html);
			}
			if (flag == 1) {
				html.println("</table>");
			}
			html.println("</li>");
			i++;
		}
		html.println("</ol>");
		return;
	}

	public PhoneLine addLine() {
		PhoneLine line = new PhoneLine(this);
		lines.add(line);
		return line;
	}
}
