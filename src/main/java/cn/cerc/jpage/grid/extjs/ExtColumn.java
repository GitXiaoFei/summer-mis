package cn.cerc.jpage.grid.extjs;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.fields.BooleanField;
import cn.cerc.jpage.fields.DateField;
import cn.cerc.jpage.fields.AbstractField;

public class ExtColumn extends Component {
	private AbstractField field;
	private String text;
	private String width;
	private String dataIndex;
	private String editor;
	private String renderer;
	private boolean sortable;
	private boolean locked;

	public ExtColumn(AbstractField field) {
		this.field = field;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public String getEditor() {
		return editor;
	}

	public ExtColumn setEditor(String editor) {
		this.editor = editor;
		return this;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getRenderer() {
		return renderer;
	}

	public ExtColumn setRenderer(String renderer) {
		// 取值范例：
		// 日期：Ext.util.Format.dateRenderer('m/d/Y')
		// 金额：'usMoney' //注意要带上单引号！
		// renderer:function(value), 如正负数: numberChange
		// renderer:function(value,metaData,record,rowIndex,colIndex,store)
		this.renderer = renderer;
		return this;
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("{");
		if (this.editor != null)
			html.print("'editor':%s,", this.editor);
		if (this.renderer != null)
			html.print("'renderer':%s,", this.renderer);
		if (!field.isReadonly()) {
			if (field instanceof BooleanField)
				html.print("'xtype':'checkcolumn',");
		}
		if (field instanceof DateField)
			html.print("'xtype':'datecolumn','format':'Y-m-d',");
		html.print("'dataIndex':'%s',", dataIndex != null ? dataIndex : "");
		html.print("'width':'%s',", width != null ? width : "");
		html.print("'text':'%s',", text != null ? text : "");
		if (field.getAlign() != null)
			html.print("'align':'%s',", field.getAlign());
		html.print("'sortable':%s,", this.sortable ? "true" : "false");
		html.print("'locked':%s", this.locked ? "true" : "false");
		html.print("}");
	}
}
