package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Image;

/**
 * 
 * @author 张弓
 *
 */
public class Block301 extends Component {
	private Image leftIcon = new Image();
	private String title = "(title)";
	private String describe = "(describe)";
	private Image rightIcon = new Image();
	private UrlRecord operator;

	/**
	 * 用于生成厂商、客户、帐套选择
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block301(Component owner) {
		super(owner);
		operator = new UrlRecord();
		leftIcon.setSrc("jui/phone/block301-leftIcon.jpg");
		leftIcon.setRole("icon");

		rightIcon.setSrc("jui/phone/block301-rightIcon.png");
		rightIcon.setRole("right");
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block301'>");
		leftIcon.output(html);
		html.print("<a href='%s'>", operator.getUrl());
		html.print("<div>");
		html.print("<div role='title'>");
		html.print("<span role='title'>%s</span>", this.title);
		rightIcon.output(html);
		html.print("</div>");
		html.print("<div role='describe'>%s</div>", this.describe);
		html.print("</div>");
		html.print("</a>");
		html.print("<div style='clear: both'></div>");
		html.print("</div>");
	}

	public Image getLeftIcon() {
		return leftIcon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public UrlRecord getOperator() {
		return operator;
	}

	public void setOperator(UrlRecord operator) {
		this.operator = operator;
	}

	public Image getRightIcon() {
		return rightIcon;
	}
}
