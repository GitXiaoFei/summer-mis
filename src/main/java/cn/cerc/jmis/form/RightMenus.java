package cn.cerc.jmis.form;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jbean.core.AbstractHandle;
import cn.cerc.jmis.page.IMenuBar;

public class RightMenus extends AbstractHandle {
	private List<IMenuBar> items = new ArrayList<>();

	public List<IMenuBar> getItems() {
		return items;
	}

	public void setItems(List<IMenuBar> items) {
		this.items = items;
	}

}
