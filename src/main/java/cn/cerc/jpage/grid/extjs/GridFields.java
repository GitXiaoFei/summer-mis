package cn.cerc.jpage.grid.extjs;

import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.common.HtmlWriter;
import cn.cerc.jpage.fields.ExpendField;
import cn.cerc.jpage.fields.Field;
import cn.cerc.jpage.grid.Grid;

public class GridFields extends Component {
	private Grid grid;

	@Override
	public void output(HtmlWriter html) {
		html.println("[");
		for (int i = 0; i < grid.getFields().size(); i++) {
			Field field = grid.getFields().get(i);
			if (field instanceof ExpendField)
				continue;
			html.print(field.createTitle().toString());
			if (i < grid.getFields().size() - 1)
				html.println(",");
		}
		html.print("]");
	}

	public Grid getGrid() {
		return grid;
	}

	public GridFields setGrid(Grid grid) {
		this.grid = grid;
		return this;
	}
}
