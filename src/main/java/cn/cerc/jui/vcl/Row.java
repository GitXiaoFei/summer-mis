package cn.cerc.jui.vcl;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.cerc.jpage.core.Component;
import cn.cerc.jui.vcl.columns.AbstractColumn;

public class Row extends Component {
	private Map<AbstractColumn, Object> items = new LinkedHashMap<>();

	public Row(Component owner) {
		super(owner);
	}

	public void add(AbstractColumn col, Object componet) {
		items.put(col, componet);
	}

	public Map<AbstractColumn, Object> getItems() {
		return items;
	}

}
