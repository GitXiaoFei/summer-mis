package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jdb.core.TDate;
import cn.cerc.jdb.core.TDateTime;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.DataSource;
import cn.cerc.jpage.core.HtmlText;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.IField;
import cn.cerc.jpage.other.BuildText;
import cn.cerc.jpage.other.BuildUrl;
import net.sf.json.JSONObject;

public abstract class AbstractField extends Component implements IField {
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
	protected BuildUrl buildUrl;
	//
	protected DataSource dataSource;

	private boolean visible = true;

	protected String oninput;

	protected String onclick;

	public AbstractField(Component owner, String name, int width) {
		super(owner);
		if (owner != null) {
			if ((owner instanceof DataSource)) {
				this.dataSource = (DataSource) owner;
				dataSource.addField(this);
				this.setReadonly(dataSource.isReadonly());
			}
		}
		this.name = name;
		this.width = width;
	}

	public HtmlText getMark() {
		return mark;
	}

	public AbstractField setMark(HtmlText mark) {
		this.mark = mark;
		return this;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int getWidth() {
		return width;
	}

	public String getShortName() {
		if (this.shortName != null)
			return this.shortName;
		return this.getName();
	}

	public AbstractField setWidth(int width) {
		this.width = width;
		return this;
	}

	public AbstractField setShortName(String shortName) {
		this.shortName = shortName;
		return this;
	}

	public AbstractField setAlign(String align) {
		this.align = align;
		return this;
	}

	public AbstractField setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String getAlign() {
		return align;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getField() {
		return field;
	}

	public AbstractField setField(String field) {
		this.field = field;
		if (this.getId() == null || this.getId().startsWith("component"))
			this.setId(field);
		return this;
	}

	public abstract String getText(Record ds);

	/**
	 * 
	 * @param rs
	 *            当前记录集
	 * @return 返回输出文本
	 */
	protected String getDefaultText(Record rs) {
		if (rs == null)
			return null;
		if (buildText != null) {
			HtmlWriter html = new HtmlWriter();
			buildText.outputText(rs, html);
			return html.toString();
		}
		return rs.getString(getField());
	}

	public BuildText getBuildText() {
		return buildText;
	}

	public AbstractField createText(BuildText buildText) {
		this.buildText = buildText;
		return this;
	}

	public String getCSSClass_phone() {
		return CSSClass_phone;
	}

	public void setCSSClass_phone(String cSSClass_phone) {
		CSSClass_phone = cSSClass_phone;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public AbstractField setReadonly(boolean readonly) {
		this.readonly = readonly;
		return this;
	}

	public String getValue() {
		return value;
	}

	public AbstractField setValue(String value) {
		this.value = value;
		return this;
	}

	public boolean isAutofocus() {
		return autofocus;
	}

	public AbstractField setAutofocus(boolean autofocus) {
		this.autofocus = autofocus;
		return this;
	}

	public boolean isRequired() {
		return required;
	}

	public AbstractField setRequired(boolean required) {
		this.required = required;
		return this;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public AbstractField setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
		return this;
	}

	public String getPattern() {
		return pattern;
	}

	public AbstractField setPattern(String pattern) {
		this.pattern = pattern;
		return this;
	}

	public boolean isHidden() {
		return hidden;
	}

	public AbstractField setHidden(boolean hidden) {
		this.hidden = hidden;
		return this;
	}

	@Override
	public void output(HtmlWriter html) {
		Record record = dataSource != null ? dataSource.getDataSet().getCurrent() : null;
		if (this.hidden) {
			outputInput(html, record);
		} else {
			html.println("<label for=\"%s\">%s</label>", this.getId(), this.getName() + "：");
			outputInput(html, record);
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

	public AbstractField setDialog(String dialog) {
		this.dialog = dialog;
		return this;
	}

	public void createUrl(BuildUrl build) {
		this.buildUrl = build;
	}

	public BuildUrl getBuildUrl() {
		return buildUrl;
	}

	public Title createTitle() {
		Title title = new Title();
		title.setName(this.getField());
		return title;
	}

	public void updateField() {
		if (dataSource != null) {
			String field = this.getId();
			if (field != null && !"".equals(field))
				dataSource.updateValue(this.getId(), this.getField());
		}
	}

	public void setDataView(DataSource dataView) {
		this.dataSource = dataView;
	}

	public String getOninput() {
		return oninput;
	}

	public AbstractField setOninput(String oninput) {
		this.oninput = oninput;
		return this;
	}

	public String getOnclick() {
		return onclick;
	}

	public AbstractField setOnclick(String onclick) {
		this.onclick = onclick;
		return this;
	}

	@Override
	public String getTitle() {
		return this.getName();
	}

	public class Editor {
		private String xtype;

		public Editor(String xtype) {
			super();
			this.xtype = xtype;
		}

		public String getXtype() {
			return xtype;
		}

		public void setXtype(String xtype) {
			this.xtype = xtype;
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public AbstractField setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	public String getString() {
		if (dataSource == null)
			throw new RuntimeException("owner is null.");
		if (dataSource.getDataSet() == null)
			throw new RuntimeException("owner.dataSet is null.");
		return dataSource.getDataSet().getString(this.getField());
	}

	public boolean getBoolean() {
		String val = this.getString();
		return "1".equals(val) || "true".equals(val);
	}

	public boolean getBoolean(boolean def) {
		String val = this.getString();
		if (val == null)
			return def;
		return "1".equals(val) || "true".equals(val);
	}

	public int getInt() {
		String val = this.getString();
		if (val == null || "".equals(val))
			return 0;
		return Integer.parseInt(val);
	}

	public int getInt(int def) {
		String val = this.getString();
		if (val == null || "".equals(val))
			return def;
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			return def;
		}
	}

	public double getDouble() {
		String val = this.getString();
		if (val == null || "".equals(val))
			return 0;
		return Double.parseDouble(val);
	}

	public double getDouble(double def) {
		String val = this.getString();
		if (val == null || "".equals(val))
			return def;
		try {
			return Double.parseDouble(val);
		} catch (Exception e) {
			return def;
		}
	}

	public TDateTime getDateTime() {
		String val = this.getString();
		if (val == null)
			return null;
		return TDateTime.fromDate(val);
	}

	public TDate getDate() {
		String val = this.getString();
		if (val == null)
			return null;
		TDateTime obj = TDateTime.fromDate(val);
		if (obj == null)
			return null;
		return new TDate(obj.getData());
	}

	public String getString(String def) {
		String result = this.getString();
		return result != null ? result : def;
	}

	public TDate getDate(TDate def) {
		TDate result = this.getDate();
		return result != null ? result : def;
	}

	public TDateTime getDateTime(TDateTime def) {
		TDateTime result = this.getDateTime();
		return result != null ? result : def;
	}

	public class Title {
		private String name;
		private String type;
		private String dateFormat;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getDateFormat() {
			return dateFormat;
		}

		public void setDateFormat(String dateFormat) {
			this.dateFormat = dateFormat;
		}

		@Override
		public String toString() {
			JSONObject json = new JSONObject();
			json.put("name", this.name);
			if (this.type != null)
				json.put("type", this.type);
			if (this.dateFormat != null)
				json.put("dateFormat", this.dateFormat);
			return json.toString().replace("\"", "'");
		}
	}

}
