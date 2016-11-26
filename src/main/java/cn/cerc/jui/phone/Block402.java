package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Label;
import cn.cerc.jpage.vcl.TextBox;

/**
 * 
 * @author 张弓
 *
 */
public class Block402 extends Component {
	private String title = "(title)";
	private Image product = new Image();
	private Image add = new Image();
	private Image diff = new Image();
	private Label describe = new Label();
	private Label remark = new Label();
	private TextBox input = new TextBox();

	/**
	 * 进出库单据明细之显示与数量修改
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block402(Component owner) {
		super(owner);
		product.setRole("product");
		product.setSrc("jui/phone/block402-product.png");

		describe.setRole("describe");
		describe.setText("(describe)");

		remark.setRole("remark");
		remark.setText("(remark)");
		// 减号
		diff.setRole("diff");
		diff.setSrc("jui/phone/block402_diff.png");
		diff.setOnclick("diffClick()");
		// 加号
		add.setRole("add");
		add.setSrc("jui/phone/block402_add.png");
		add.setOnclick("addClick()");
		// 输入框
		input.setType("number");
		input.setValue("0");
		input.setMaxlength("10");
		input.setOnclick("inputEvent(value,this)");
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<!-- %s -->", this.getClass().getName());
		html.print("<section class='block402'>");
		html.print("<div class='up_con'>");
		product.output(html);
		html.print("<div class='name'>%s</div>", this.title);
		html.print("<div class='c_buy'>");
		describe.output(html);
		html.print("<span class='gobuy'>");
		diff.output(html);
		input.output(html);
		add.output(html);
		html.print("</span>	");
		html.print("</div>");
		html.print("</div>");
		html.print("<div class='info'>%s</div>", remark.toString());
		html.print("</section>");
	}

	public Label getDescribe() {
		return describe;
	}

	public void setDescribe(Label describe) {
		this.describe = describe;
	}

	public TextBox getInput() {
		return input;
	}

	public Image getProduct() {
		return product;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Label getRemark() {
		return remark;
	}

	public Image getAdd() {
		return add;
	}

	public Image getDiff() {
		return diff;
	}
}
