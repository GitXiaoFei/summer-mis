package cn.cerc.jui.phone;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.UrlRecord;
import cn.cerc.jpage.vcl.Image;

/**
 * 系统菜单显示组件
 * 
 * @author HuangRongjun
 *
 */
public class BlockMenu extends Component {
	private Map<UrlRecord, Image> menus = new LinkedHashMap<>();

	public BlockMenu(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.println("<div class='bolcokMenu' role='row'>");

		html.println("<ul id='Board1' class='menuBoard'>");
		if (menus.size() == 0) {
			html.println("<li>");
			html.println("	<div class='row1'>");
			html.println("		<img src='jui/phone/block301-leftIcon.png'>");
			html.println("	</div>");
			html.println("	<div class='row2'>");
			html.println("		菜单名称");
			html.println("	</div>");
			html.println("</li>");
		}

		for (UrlRecord url : menus.keySet()) {
			html.println("<li>");
			html.println("	<div class='row1'>");
			html.println("		<a href='%s'>%s</a>", url.getUrl(), menus.get(url));
			html.println("	</div>");
			html.println("	<div class='row2'>");
			html.println("		<a href='%s'>%s</a>", url.getUrl(), url.getName());
			html.println("	</div>");
			html.println("</li>");
		}
		html.println("</ul>");
	}

	public void addMenu(UrlRecord url, Image image) {
		menus.put(url, image);
	}

	public Map<UrlRecord, Image> getMenus() {
		return menus;
	}

}
