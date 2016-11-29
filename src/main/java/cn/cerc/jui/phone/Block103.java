package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;

/**
 * 
 * @author 张弓
 *
 */
public class Block103 extends Component {
	/**
	 * 显示商品详情，方便加入购物车
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block103(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block103'>");
		html.println("</div>");
	}
}
