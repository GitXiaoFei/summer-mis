package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

public class TextBox extends Component {
	private Label caption;
	private String name;
	private String value;
	private String placeholder;
	private boolean readonly;

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
		html.print("<input id='%s' name='%s'", this.getId(), this.getName());
		if (value != null)
			html.print(" value='%s'", this.value);
		if (placeholder != null)
			html.print(" placeholder='%s'", this.placeholder);
		if(this.readonly)
			html.print(" readonly");
		html.println(">");
	}

	public Label getCaption() {
		if (caption == null)
			caption = new Label();
		return caption;
	}

	public void setCaption(Label caption) {
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

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

}
