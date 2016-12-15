package cn.cerc.jui.phone;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.vcl.Span;
/**
 * 标题
 * @author rascal.guo
 *
 */
public class Block121 extends Component {
	private Span title = new Span();

	public Block121(Component owner) {
		super(owner);
	}

	@Override
	public void output(HtmlWriter html) {
		html.println("<!-- %s -->", this.getClass().getName());
		html.print("<div class='block121'>");
		html.print("<header id='header' class='header'>");
		html.print("<a href='javascript:window.location.go(-1);'  class='arrow-left'>");
		html.print("<img src='/img/icon/icon_arrow_left.png'  width='100%'></a>");
		html.print("<h1 class='title'>");
		this.title.output(html);
		html.print("</h1>");
		html.print("</header>");
		html.println("</div>");
	}

	public Span getTitle() {
		return title;
	}
	
	public void setTitle(String text){
		this.title.setText(text);
	}
}
