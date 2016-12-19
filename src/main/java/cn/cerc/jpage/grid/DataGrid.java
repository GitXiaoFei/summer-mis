package cn.cerc.jpage.grid;

import static cn.cerc.jdb.other.utils.roundTo;

import java.util.List;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.fields.IField;
import cn.cerc.jpage.grid.line.AbstractGridLine;
import cn.cerc.jpage.grid.line.ExpenderGridLine;

public class DataGrid extends AbstractGrid {
	private IColumnsManager manager;
	// 扩展对象
	private AbstractGridLine expender;

	public DataGrid(Component owner) {
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
		List<IField> fields = this.getMasterLine().getFields();
		if (manager != null)
			fields = manager.Reindex(fields);

		double sumFieldWidth = 0;
		for (IField column : fields)
			sumFieldWidth += column.getWidth();

		if (sumFieldWidth < 0)
			throw new RuntimeException("总列宽不允许小于1");
		if (sumFieldWidth > 60)
			throw new RuntimeException("总列宽不允许大于60");

		html.print("<table class=\"%s\"", this.getCSSClass_PC());
		if (this.getCSSStyle() != null)
			html.print(" style=\"%s\"", this.getCSSStyle());
		html.println(">");

		html.println("<tr>");
		for (IField column : fields) {
			html.print("<th");
			if (column.getWidth() == 0)
				html.print(" style=\"display:none\"");
			else {
				double val = roundTo(column.getWidth() / sumFieldWidth * 100, -2);
				html.print(" width=\"%f%%\"", val);
			}
			html.print(">");
			html.print(column.getTitle());
			html.println("</th>");
		}
		html.println("</tr>");
		int i = pages.getBegin();
		while (i <= pages.getEnd()) {
			dataSet.setRecNo(i + 1);
			for (int lineNo = 0; lineNo < this.getLines().size(); lineNo++) {
				AbstractGridLine line = this.getLine(lineNo);
				if (line instanceof ExpenderGridLine)
					line.getCell(0).setColSpan(this.getLine(0).getFields().size());
				line.output(html);
			}
			// 下一行
			i++;
		}
		html.println("</table>");
		return;
	}

	public IColumnsManager getManager() {
		return manager;
	}

	public void setManager(IColumnsManager manager) {
		this.manager = manager;
	}

	@Override
	public Component getExpender() {
		if (expender == null) {
			expender = new ExpenderGridLine(this);
			this.getLines().add(expender);
		}
		return expender;
	}
}
