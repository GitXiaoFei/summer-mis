package cn.cerc.jui.phone;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Button;

/**
 * 
 * @author 张弓
 *
 */
public class Block992 extends Component {
	private List<Button> items = new ArrayList<>();

	/**
	 * 底部状态栏：1个功能按钮+提示文字
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block992(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.print("<!-- %s -->", this.getClass().getName());
		html.println("<div class=\"block992\">");
		for (Button button : items)
			button.output(html);
		html.println("</div>");
	}

	public Button addButton(String caption) {
		Button button = new Button();
		button.setText(caption);
		items.add(button);
		return button;
	}
}
