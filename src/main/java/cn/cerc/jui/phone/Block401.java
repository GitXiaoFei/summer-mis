package cn.cerc.jui.phone;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Button;
import cn.cerc.jpage.vcl.Image;
import cn.cerc.jpage.vcl.Label;

/**
 * 
 * @author 张弓
 *
 */
public class Block401 extends Component {
	private String title = "(title)";
	private Image product = new Image();
	private List<Image> images = new ArrayList<>();
	private Label remark = new Label();
	private Label describe = new Label();
	private Button button = new Button();

	/**
	 * 显示商品摘要，方便加入购物车
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block401(Component owner) {
		super(owner);
		product.setRole("product");
		product.setAlt("(product)");
		product.setSrc("jui/phone/block401-product.png");
		button.setText("(button)");

		remark.setRole("remark");
		remark.setText("(remark)");
		describe.setRole("describe");
		describe.setText("(describe)");
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<!-- %s -->", this.getClass().getName());
		html.print("<section class='block401'>");
		html.print("<div class='up_con'>");
		product.output(html);
		html.print("<div role='title'>%s</div>", this.title);
		html.print("<div role='operation'>");

		for (Image image : images) {
			html.print("<span role='image'>");
			image.output(html);
			html.print("</span>");
		}

		describe.output(html);
		html.print("</div>");
		html.print("</div>");
		html.print("<div class='info'>");
		remark.output(html);
		button.output(html);
		html.print("</div>");
		html.println("</section>");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public void addImage(String imgUrl) {
		Image image = new Image();
		image.setSrc(imgUrl);
		images.add(image);
	}

	public Image getProduct() {
		return product;
	}

	public Label getRemark() {
		return remark;
	}

	public Label getDescribe() {
		return describe;
	}
}
