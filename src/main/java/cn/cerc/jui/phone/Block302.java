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
public class Block302 extends Component {
	private String title = "(title)";
	private String describe = "(describe)";
	private Image rightIcon = new Image();
	private UrlRecord operator;

	/**
	 * 用于显示会员资料
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block302(Component owner) {
		super(owner);
		operator = new UrlRecord();
		rightIcon.setSrc("jui/phone/block301-rightIcon.png");
		rightIcon.setRole("right");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block302'>");
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
		html.println("</div>");
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
