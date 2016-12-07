package cn.cerc.jui.vcl;

import java.util.LinkedHashMap;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class ComboBox extends Component {
	private LinkedHashMap<String, String> options = new LinkedHashMap<>(6);
	private String selectId;
	private String name;
	private DataSet ds;

	@Override
	public void output(HtmlWriter html) {
		html.println("<select id='%s' name='%s'>", this.getId(), name);
		for (String key : options.keySet()) {
			if (key.equals(selectId)) {
				html.println("<option value='%s' selected='selected' >%s</option>", key, options.get(key));
				continue;
			}
			html.print("<option value='%s' >%s</option>", key, options.get(key));
		}
		html.println("</select>");
	}

	public void setDataSet(DataSet ds, String keyField, String valueField) {
		this.ds = ds;
		while (ds.fetch()) {
			this.options.put(ds.getString(keyField), ds.getString(valueField));
		}
	}

	public ComboBox(Component component) {
		super(component);
	}

	public LinkedHashMap<String, String> getOptions() {
		return options;
	}

	public void setOptions(LinkedHashMap<String, String> options) {
		this.options = options;
	}

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataSet getDataSet() {
		return ds;
	}

}