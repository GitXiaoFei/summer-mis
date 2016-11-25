package cn.cerc.jui.phone;

import java.util.ArrayList;
import java.util.List;

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
public class Block107 extends Component {
	private TextBox input = new TextBox();
	private Image image = new Image();
	private List<String> items = new ArrayList<>();
	private String childPage = "";
	private String dataUrl = "";

	/**
	 * 用于生成类别的开窗选择，如商品品牌、大中类、系列，地址等
	 * 
	 * @param owner
	 *            内容显示区
	 */
	public Block107(Component owner) {
		super(owner);
		input.setPlaceholder("当前条件：");
		input.setReadonly(true);
		image.setSrc("jui/phone/block107-expand.png");
	}

	@Override
	public void output(HtmlWriter html) {
		String onclick = String.format("javascript:showChoice(\"%s\");", this.getId());
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block107'>", this.getId());
		html.print("<div onclick='%s'>", onclick);
		input.setId(this.getId() + "input");
		input.output(html);
		html.println("");
		image.output(html);
		html.println("</div>");
		html.println("<div id='%schoice' class='choice2'>", this.getId());
		html.print("<div class='choice3'>");
		html.println("</div>");
		html.print("<div id='%slist' class='choice4'>", this.getId());
		html.print("<ul class=''>");
		for (String item : items)
			outputChoiceItem(html, item, this.getId(), this.childPage, this.dataUrl);
		html.println("</ul>");
		html.println("</div>");
		html.println("</div>");
		html.println("</div>");
	}

	public TextBox getInput() {
		return input;
	}

	public Label getCaption() {
		return input.getCaption();
	}

	public List<String> getItems() {
		return items;
	}

	public String getChildPage() {
		return childPage;
	}

	public Block107 setChildPage(String childPage) {
		this.childPage = childPage;
		return this;
	}

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public static void outputChoiceItem(HtmlWriter html, String value, String choiceId, String childPage,
			String dataUrl) {
		html.print("<li onclick='javascript:choiceItem(\"%s\", \"%s\", \"%s\", \"%s\")'>%s</li>", choiceId, value,
				childPage, dataUrl, value);
	}
}

// 调用范例：
// jspChildPage.addStyleFile("jui/phone/phone-block.css");
// jspChildPage.addScriptFile("jui/phone/phone-block.js");
// ActionForm aform = new ActionForm();
//// 建立全文检索查询条件
// Block100 block1 = new Block100(aform);
// block1.getInput().setName("searchBox");
// jspChildPage.add("searchBox", aform);
//
//// 建立商品品牌查询条件
// block107 block2 = new block107(aform);
// block2.getCaption().setText("商品品牌：");
// block2.getInput().setValue("所有品牌");
// block2.getItems().add("所有品牌");
// block2.getItems().add("品牌1");
// block2.getItems().add("品牌2");
// block2.getItems().add("品牌3");
//
//// 建立商品大类查询条件
// block107 block3 = new block107(aform);
// block3.setId("pType1");
// block3.getCaption().setText("商品大类：");
// block3.getInput().setValue("所有大类");
// block3.getItems().add("所有大类");
// block3.getItems().add("钓杆");
// block3.getItems().add("渔轮");
//
//// 建立商品中类查询条件
// block107 block4 = new block107(aform);
// block4.setId("pType2");
// block4.getCaption().setText("商品中类：");
// block4.getInput().setValue("所有中类");
//
//// 建立商品系列查询条件
// block107 block5 = new block107(aform);
// block5.setId("pType3");
// block5.getCaption().setText("商品系列：");
// block5.getInput().setValue("所有系列");
//
// 设置大类调用中类，中类调用系列
// block3.setChildPage(block4.getId()).setDataUrl("TFrmProSearch.getPartType2");
// block4.setChildPage(block5.getId()).setDataUrl("TFrmProSearch.getPartType3");
//
// 大类调用中类时，响应数据源
// public IPage getPartType2() {
// HtmlPage page = new HtmlPage(this);
// String param = this.getRequest().getParameter("parent");
// HtmlWriter html = page.getContent();
// String choiceId = "pType2";
// String childPage = "pType3";
// String dataUrl = "TFrmProSearch.getPartType3";
// block107.outputChoiceItem(html, param, choiceId, childPage, dataUrl);
// block107.outputChoiceItem(html, "所有中类", choiceId, "", "");
// block107.outputChoiceItem(html, "A中类", choiceId, childPage, dataUrl);
// block107.outputChoiceItem(html, "B中类", choiceId, childPage, dataUrl);
// block107.outputChoiceItem(html, "C中类", choiceId, childPage, dataUrl);
// return page;
// }
//
// 中类调用系列时，响应数据源
// public IPage getPartType3() {
// HtmlPage page = new HtmlPage(this);
// String param = this.getRequest().getParameter("parent");
// HtmlWriter html = page.getContent();
// String choiceId = "pType3";
// block107.outputChoiceItem(html, param, choiceId, "", "");
// block107.outputChoiceItem(html, "所有系列", choiceId, "", "");
// block107.outputChoiceItem(html, "A系列", choiceId, "", "");
// block107.outputChoiceItem(html, "B系列", choiceId, "", "");
// block107.outputChoiceItem(html, "C系列", choiceId, "", "");
// return page;
// }