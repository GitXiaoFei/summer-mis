package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.BuildText;
import cn.cerc.jpage.common.BuildUrl;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.common.Expender;
import cn.cerc.jpage.common.HtmlText;
import cn.cerc.jpage.common.IField;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.form.Title;
import cn.cerc.jpage.grid.Editor;
import cn.cerc.jpage.grid.extjs.Column;
import net.sf.json.JSONObject;

public class Field extends Component implements IField {
	private String name;
	private String shortName;
	private String align;
	private int width;
	// 数据库相关
	protected String field;
	// 自定义取值
	protected BuildText buildText;
	// 手机专用样式
	private String CSSClass_phone;
	// value
	private String value;
	// 只读否
	private boolean readonly;
	// 焦点否
	protected boolean autofocus;
	//
	protected boolean required;
	//
	protected String placeholder;
	// 正则过滤
	protected String pattern;
	//
	protected boolean hidden;
	// 角色
	protected String role;
	//
	protected String dialog;
	// 栏位说明
	private HtmlText mark;
	//
	private BuildUrl buildUrl;
	//
	protected DataView dataView;
	//
	private Expender expender;

	protected String oninput;

	protected String onclick;
	// 由extGrid调用
	private Column column;

	public Field(DataView dataView, String name, int width) {
		this.dataView = dataView;
		if (dataView != null) {
			dataView.addField(this);
			this.setReadonly(dataView.isReadonly());
		}
		this.name = name;
		this.width = width;
	}

	public HtmlText getMark() {
		return mark;
	}

	public Field setMark(HtmlText mark) {
		this.mark = mark;
		return this;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getWidth() {
		return width;
	}

	public String getShortName() {
		if (this.shortName != null)
			return this.shortName;
		return this.getName();
	}

	public Field setWidth(int width) {
		this.width = width;
		return this;
	}

	public Field setShortName(String shortName) {
		this.shortName = shortName;
		return this;
	}

	public Field setAlign(String align) {
		this.align = align;
		return this;
	}

	public Field setName(String name) {
		this.name = name;
		return this;
	}

	public String getAlign() {
		return align;
	}

	public String getName() {
		return name;
	}

	public String getField() {
		return field;
	}

	public Field setField(String field) {
		this.field = field;
		return this;
	}

	public String getText(Record ds) {
		if (buildText == null)
			return null;
		HtmlWriter html = new HtmlWriter();
		buildText.outputText(ds, html);
		return html.toString();
	}

	public BuildText getBuildText() {
		return buildText;
	}

	public Field createText(BuildText buildText) {
		this.buildText = buildText;
		return this;
	}

	public String getCSSClass_phone() {
		return CSSClass_phone;
	}

	public void setCSSClass_phone(String cSSClass_phone) {
		CSSClass_phone = cSSClass_phone;
	}

	@Override
	public String getId() {
		if (super.getId() == null)
			return field;
		else
			return super.getId();
	}

	public boolean isReadonly() {
		return readonly;
	}

	public Field setReadonly(boolean readonly) {
		this.readonly = readonly;
		return this;
	}

	public String getValue() {
		return value;
	}

	public Field setValue(String value) {
		this.value = value;
		return this;
	}

	public boolean isAutofocus() {
		return autofocus;
	}

	public Field setAutofocus(boolean autofocus) {
		this.autofocus = autofocus;
		return this;
	}

	public boolean isRequired() {
		return required;
	}

	public Field setRequired(boolean required) {
		this.required = required;
		return this;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public Field setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
		return this;
	}

	public String getPattern() {
		return pattern;
	}

	public Field setPattern(String pattern) {
		this.pattern = pattern;
		return this;
	}

	public boolean isHidden() {
		return hidden;
	}

	public Field setHidden(boolean hidden) {
		this.hidden = hidden;
		return this;
	}

	@Override
	public void output(HtmlWriter html) {
		Record dataSet = dataView != null ? dataView.getRecord() : null;
		if (this.hidden) {
			outputInput(html, dataSet);
		} else {
			html.println("<label for=\"%s\">%s</label>", this.getId(), this.getName() + "：");
			outputInput(html, dataSet);
			if (this.dialog != null) {
				html.print("<span>");
				html.print("<a href=\"javascript:%s('%s')\">", this.dialog, this.getId());
				html.print("<img src=\"images/select-pic.png\">");
				html.print("</a>");
				html.println("</span>");
			} else {
				html.println("<span></span>");
			}
		}
	}

	protected void outputInput(HtmlWriter html, Record dataSet) {
		if (this.hidden) {
			html.print("<input");
			html.print(" type=\"hidden\"");
			html.print(" name=\"%s\"", this.getId());
			html.print(" id=\"%s\"", this.getId());
			String value = this.getText(dataSet);
			if (value != null)
				html.print(" value=\"%s\"", value);
			html.println("/>");
		} else {
			html.print("<input");
			html.print(" type=\"text\"");
			html.print(" name=\"%s\"", this.getId());
			html.print(" id=\"%s\"", this.getId());
			String value = this.getText(dataSet);
			if (value != null)
				html.print(" value=\"%s\"", value);
			if (this.getValue() != null) {
				html.print(" value=\"%s\"", this.getValue());
			}
			if (this.isReadonly())
				html.print(" readonly=\"readonly\"");
			if (this.autofocus)
				html.print(" autofocus");
			if (this.required)
				html.print(" required");
			if (this.placeholder != null)
				html.print(" placeholder=\"%s\"", this.placeholder);
			if (this.pattern != null)
				html.print(" pattern=\"%s\"", this.pattern);
			if (this.CSSClass_phone != null)
				html.print(" class=\"%s\"", this.CSSClass_phone);
			if (this.oninput != null)
				html.print(" oninput=\"%s\"", this.oninput);
			if (this.onclick != null)
				html.print(" onclick=\"%s\"", this.onclick);
			html.println("/>");
		}
	}

	public String getDialog() {
		return dialog;
	}

	public Field setDialog(String dialog) {
		this.dialog = dialog;
		return this;
	}

	public void createUrl(BuildUrl build) {
		this.buildUrl = build;
	}

	public DataView getDataView() {
		return dataView;
	}

	@Override
	public String getString() {
		if (dataView == null)
			throw new RuntimeException("owner is null.");
		if (dataView.getRecord() == null)
			throw new RuntimeException("owner.dataSet is null.");
		return dataView.getRecord().getString(this.getField());
	}

	public BuildUrl getBuildUrl() {
		return buildUrl;
	}

	public Title createTitle() {
		Title title = new Title();
		title.setName(this.getField());
		return title;
	}

	public Expender getExpender() {
		return expender;
	}

	public Field setExpender(Expender expender) {
		this.expender = expender;
		return this;
	}

	public void updateField() {
		if (dataView != null) {
			String field = this.getId();
			if (field != null && !"".equals(field))
				dataView.updateValue(this.getId(), this.getField());
		}
	}

	public void setDataView(DataView dataView) {
		this.dataView = dataView;
	}

	public String getOninput() {
		return oninput;
	}

	public Field setOninput(String oninput) {
		this.oninput = oninput;
		return this;
	}

	public String getOnclick() {
		return onclick;
	}

	public Field setOnclick(String onclick) {
		this.onclick = onclick;
		return this;
	}

	public Column getColumn() {
		if (column == null) {
			column = new Column(this);
			column.setText(this.getName());
			column.setDataIndex(this.getField());
			column.setLocked(false);
			column.setSortable(true);
			if (!this.isReadonly()) {
				Editor editor = new Editor("textfield");
				column.setEditor(JSONObject.fromObject(editor).toString().replace("\"", "'"));
			}
		}
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}
}
