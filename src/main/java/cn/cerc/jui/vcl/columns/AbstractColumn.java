package cn.cerc.jui.vcl.columns;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jui.vcl.BaseGrid;

public class AbstractColumn extends Component {
	private String title;// 列头
	private String field;

	public AbstractColumn(Component owner) {
		super(owner);
	}

	public AbstractColumn(Component owner, String field, String title) {
		super(owner);
		this.setField(field);
		this.setTitle(title);
	}
	
	public AbstractColumn(Component owner, String title) {
		super(owner);
		this.setTitle(title);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String format(Object value) {
		if (value instanceof Record)
			return ((Record) value).getString(this.field);
		else
			return value.toString();
	}

	public void writeInput(HtmlWriter html, Record record){
		html.print("<div>");
		html.print("%s", this.getTitle());
		html.print("<input id='%s'", this.field);
		html.print(" name='%s'", this.field);
		html.print(" value='%s'", record.getString(this.field));
		html.print(">");
		html.print("</div>");
	}
	
	public BaseGrid getGrid() {
		if (this.getOwner() instanceof BaseGrid)
			return (BaseGrid) this.getOwner();
		else
			return null;
	}
}
