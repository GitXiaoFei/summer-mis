package cn.cerc.jpage.grid.extjs;

import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.fields.ExpendField;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.grid.AbstractGrid;

public class ExtGridFields extends Component {
	private AbstractGrid grid;

	@Override
	public void output(HtmlWriter html) {
		html.println("[");
		List<AbstractField> items = grid.getFields();
		for (int i = 0; i < items.size(); i++) {
			AbstractField field = items.get(i);
			if (field instanceof ExpendField)
				continue;
			html.print(field.createTitle().toString());
			if (i < items.size() - 1)
				html.println(",");
		}
		html.print("]");
	}

	public AbstractGrid getGrid() {
		return grid;
	}

	public ExtGridFields setGrid(AbstractGrid grid) {
		this.grid = grid;
		return this;
	}
}
