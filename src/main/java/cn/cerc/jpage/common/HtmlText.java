package cn.cerc.jpage.common;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

/*
 * 专用于简单或原始文字输出
 */
public class HtmlText extends Component {
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
