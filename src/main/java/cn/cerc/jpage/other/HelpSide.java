package cn.cerc.jpage.other;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.form.UrlMenu;

public class HelpSide extends Component {
	private String title = "操作提示";
	private String content;
	private UrlMenu operaUrl;
	private List<String> lines = new ArrayList<>();

	public HelpSide() {
		super();
	}

	public HelpSide(Component owner) {
		super(owner);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void addLine(String value) {
		lines.add(value);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<section>");
		html.print("<div class=\"title\">");
		html.print(this.title);
		if (operaUrl != null) {
			operaUrl.output(html);
		}
		html.println("</div>");
		html.println("<div class=\"contents\">");
		if (this.content != null)
			html.println("<p>%s</p>", this.content);
		for (String line : lines)
			html.println("<p>%s</p>", line);
		html.println("</div>");
		html.println("</section>");
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public UrlMenu getOperaUrl() {
		if (operaUrl == null) {
			operaUrl = new UrlMenu(null);
			operaUrl.setStyle("float:right;margin-bottom:0.25em");
		}
		return operaUrl;
	}

	public void setOperaUrl(UrlMenu operaUrl) {
		this.operaUrl = operaUrl;
	}

}
