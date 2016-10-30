package cn.cerc.jpage.grid;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.common.HtmlWriter;
import cn.cerc.jpage.common.TMutiPage;

public class PhoneGrid extends Grid {

	public PhoneGrid() {
		super();
	}

	public PhoneGrid(Component owner) {
		super(owner);
	}

	@Override
	public void outputGrid(HtmlWriter html) {
		DataSet dataSet = this.getDataSet();
		TMutiPage pages = this.getPages();
		if (dataSet.size() == 0)
			return;

		html.println(String.format("<ol class=\"%s\">", this.getCSSClass_Phone()));

		int i = pages.getBegin();
		while (i <= pages.getEnd()) {
			dataSet.setRecNo(i + 1);
			int flag = 0;
			html.println("<li>");
			for (PhoneLine line : this.getLines()) {
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

}
