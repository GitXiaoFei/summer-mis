package cn.cerc.jpage.grid.extjs;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.BuildUrl;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.fields.BooleanField;
import cn.cerc.jpage.fields.ExpendField;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.grid.AbstractGrid;
import net.sf.json.JSONArray;

public class ExtGridData extends Component {
	private AbstractGrid grid;

	@Override
	public void output(HtmlWriter html) {
		DataSet dataSet = grid.getDataSet();
		html.println("[");
		if (dataSet.size() == 0) {
			html.println("]");
			return;
		}
		// dataSet.first();
		// while (dataSet.fetch()) {
		int i = grid.getPages().getBegin();
		while (i <= grid.getPages().getEnd()) {
			dataSet.setRecNo(i + 1);
			JSONArray json = new JSONArray();
			for (AbstractField field : grid.getFields()) {
				if (field instanceof ExpendField)
					continue;
				// json.add(field.getText(dataSet.getCurrent()));

				Record record = dataSet.getCurrent();
				BuildUrl build = field.getBuildUrl();
				if (build != null) {
					UrlRecord url = new UrlRecord();
					build.buildUrl(record, url);
					if (!"".equals(url.getUrl())) {
						StringBuffer extUrl = new StringBuffer();
						extUrl.append(String.format("<a href=\"%s\"", url.getUrl()));
						if (url.getTitle() != null)
							extUrl.append(String.format(" title=\"%s\"", url.getTitle()));
						extUrl.append(String.format(">%s</a>", field.getText(record)));
						json.add(extUrl.toString());
					} else {
						json.add(field.getText(record));
					}
				} else {
					if (!field.isReadonly() && field instanceof BooleanField)
						json.add(record.getBoolean(field.getField()));
					else
						json.add(field.getText(record));
				}

			}
			html.print(json.toString());
			if (dataSet.getRecNo() < dataSet.size())
				html.println(",");
			i++;
		}
		html.print("]");
	}

	public AbstractGrid getGrid() {
		return grid;
	}

	public ExtGridData setGrid(AbstractGrid grid) {
		this.grid = grid;
		return this;
	}
}
