package cn.cerc.jpage.grid.extjs;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.fields.ExpendField;
import cn.cerc.jpage.grid.ExtGrid;

public class ExtGridColumns extends Component {
	private ExtGrid grid;

	@Override
	public void output(HtmlWriter html) {
		html.print("[");
		List<ExtColumn> columns = new ArrayList<>();
		double sumFieldWidth = 0;
		for (AbstractField field : grid.getFields()) {
			if (field instanceof ExpendField)
				continue;
			sumFieldWidth += field.getWidth();
		}
		for (AbstractField field : grid.getFields()) {
			if (field instanceof ExpendField || field.getWidth() == 0)
				continue;
			ExtColumn col1 = field.getColumn();
			String val = "" + (field.getWidth() / sumFieldWidth * 98);// 预留2%给滚动条
			col1.setWidth(String.format("%s%%", val.substring(0, val.indexOf(".") + 2)));
			columns.add(col1);
		}

		for (int i = 0; i < columns.size(); i++) {
			ExtColumn column = columns.get(i);
			column.output(html);
			if (i < columns.size() - 1)
				html.println(",");
		}
		html.print("]");
	}

	public ExtGrid getGrid() {
		return grid;
	}

	public ExtGridColumns setGrid(ExtGrid grid) {
		this.grid = grid;
		return this;
	}
}
