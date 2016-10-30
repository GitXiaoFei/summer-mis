package cn.cerc.jmis.tools;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cn.cerc.jpage.common.Component;
import cn.cerc.jpage.other.UrlSide;

public class PageLink {
	private static ApplicationContext app;
	private static String xmlFile = "classpath:page-link.xml";

	public static UrlSide get(Component owner, String beanId) {
		if (app == null)
			app = new FileSystemXmlApplicationContext(xmlFile);
		if (!app.containsBean(beanId))
			return null;
		UrlSide side = app.getBean(beanId, UrlSide.class);
		side.setOwner(owner);
		return side;
	}

	public static void main(String[] args) {
		UrlSide help = get(null, "TFrmPartBrand");
		System.out.println(help.getHtml());
	}
}
