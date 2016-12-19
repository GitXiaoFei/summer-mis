package cn.cerc.jpage.grid.column;

import java.util.LinkedHashMap;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.grid.ObjectGrid;

public class LinkColumn extends AbstractColumn {
	private String url;

	LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();

	public LinkColumn(Component owner, String title, String url) {
		super(owner);
		this.setTitle(title);
		this.url = url;
	}

	public void addItem(String optionId, String optionValue) {
		this.params.put(optionId, optionValue);
	}

	@Override
	public String format(Object value) {
		String tempParam = url;
		StringBuffer sb = new StringBuffer();
		sb.append("<td>");
		if (value instanceof Record) {
			Record rec = (Record) value;
			boolean flug = true;
			for (String key : params.keySet()) {// 设置参数
				if (flug) {
					tempParam += "?" + key + "=" + rec.getField(params.get(key));
					flug = false;
					continue;
				}
				tempParam += "&" + key + "=" + rec.getField(params.get(key));
			}
			sb.append(String.format("<a class='button' href='%s'>%s</a>", tempParam, this.getTitle()));
		} else {
			sb.append(value);
		}
		sb.append("</td>");
		return sb.toString();
	}

	public void add(String name, String url) {
		ObjectGrid grid = this.getGrid();
		grid.getCurrent().addData(this, new Url(name, url));
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public class Url {
		private String name;
		private String url;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Url(String name, String url) {
			super();
			this.name = name;
			this.url = url;
		}
	}

}
