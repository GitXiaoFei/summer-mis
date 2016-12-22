package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

/**
 * 
 * 单行文本输入框
 * 
 * @author 张弓
 *
 */
public class TextBox extends Component {
	private Span caption;
	private String name;
	private String type;
	private String value;
	// 正则过滤
	protected String pattern;
	private String maxlength;
	private String placeholder;
	private boolean autofocus;
	private boolean readonly;
	private boolean required;
	private String onclick;
	private String oninput;

	public TextBox() {
		super();
	}

	public TextBox(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		if (caption != null)
			caption.output(html);
		html.print("<input");
		if (this.getId() != null)
			html.print(" id='%s'", this.getId());
		if (this.name != null)
			html.print(" name='%s'", this.getName());
		if (type != null)
			html.print(" type=\"%s\"", type);
		if (maxlength != null)
			html.print(" maxlength=%s", this.maxlength);
		if (value != null)
			html.print(" value='%s'", this.value);
		if (pattern != null)
			html.print(" pattern=\"%s\"", this.pattern);
		if (onclick != null)
			html.print(" onclick='%s'", this.onclick);
		if (oninput != null)
			html.print(" oninput='%s'", this.oninput);
		if (placeholder != null)
			html.print(" placeholder='%s'", this.placeholder);
		if (this.autofocus)
			html.print(" autofocus");
		if (this.required)
			html.print(" required");
		if (this.readonly)
			html.print(" readonly='readonly'");
		html.println(" />");
	}

	public Span getCaption() {
		if (caption == null)
			caption = new Span();
		return caption;
	}

	public void setCaption(Span caption) {
		this.caption = caption;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isAutofocus() {
		return autofocus;
	}

	public void setAutofocus(boolean autofocus) {
		this.autofocus = autofocus;
	}

	public String getType() {
		return type;
	}

	public TextBox setType(String type) {
		this.type = type;
		return this;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public String getOninput() {
		return oninput;
	}

	public void setOninput(String oninput) {
		this.oninput = oninput;
	}

}
