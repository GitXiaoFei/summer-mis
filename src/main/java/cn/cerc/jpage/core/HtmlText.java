package cn.cerc.jpage.core;

import java.util.ArrayList;
import java.util.List;

/*
 * 专用于简单或原始文字输出
 */
public class HtmlText extends Component {
	//文本内容
	private String content;
	private List<String> lines;

	public HtmlText() {
		super();
	}

	public HtmlText(Component owner) {
		super(owner);
	}

	public void output(HtmlWriter html) {
		if (content != null)
			html.print(content);
		if (lines != null) {
			for (String line : lines)
				html.println("<p>%s</p>", line);
		}
	}

	public String getContent() {
		return content;
	}

	public HtmlText setContent(String content) {
		this.content = content;
		return this;
	}

	public List<String> getLines() {
		if (lines == null)
			lines = new ArrayList<>();
		return lines;
	}

	public HtmlText setLines(List<String> lines) {
		this.lines = lines;
		return this;
	}
}
