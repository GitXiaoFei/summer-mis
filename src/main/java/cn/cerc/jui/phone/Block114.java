package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.CheckBox;
import cn.cerc.jpage.vcl.Label;
import cn.cerc.jpage.vcl.LabelBox;

public class Block114 extends Component {
	private Label label = new Label();
	private CheckBox checkBox = new CheckBox();
	private LabelBox labelBox = new LabelBox();

	/**
	 * 文本 + 选择框 + 说明
	 * <p>
	 * 例如用于显示下游品牌授权
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block114(Component owner) {
		super(owner);
		label.setText("(label)");
		labelBox.setText("(label)");
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block114'>");
		label.output(html);
		checkBox.output(html);
		labelBox.output(html);
		html.println("</div>");
	}

	public Label getLabel() {
		return label;
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public LabelBox getLabelBox() {
		return labelBox;
	}

}
