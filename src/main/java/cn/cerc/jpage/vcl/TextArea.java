package cn.cerc.jpage.vcl;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

/**
 * 多行文本输入框
 * 
 * @author 黄荣君
 *
 */
public class TextArea extends Component {
	private Span caption;
	private String name;
	private String text;
	private String placeholder;
	private int cols;
	private int rows;
	private boolean readonly;

	public TextArea() {
	}

	public TextArea(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		if (caption != null) {
			caption.output(html);
		}

		html.print("<textarea ");
		if (this.getId() != null) {
			html.print(" id='%s'", this.getId());
		}
		if (name != null) {
			html.print("name='%s' ", name);
		}
		if (cols != 0) {
			html.print("rows='%s' ", cols);
		}
		if (rows != 0) {
			html.print("rows='%s' ", rows);
		}
		if (placeholder != null) {
			html.print("placeholder=%s ", placeholder);
		}
		if (readonly) {
			html.print("readonly='readonly'");
		}
		html.print(">");

		if (text != null) {
			html.print(text);
		}
		html.print("</textarea>");
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

}
