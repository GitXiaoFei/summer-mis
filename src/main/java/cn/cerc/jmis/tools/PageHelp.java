package cn.cerc.jmis.tools;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.other.HelpSide;

public class PageHelp {
	private static ApplicationContext app;
	private static String xmlFile = "classpath:page-help.xml";

	public static HelpSide get(Component owner, String beanId) {
		if (app == null)
			app = new FileSystemXmlApplicationContext(xmlFile);
		if (!app.containsBean(beanId))
			return null;
		HelpSide side = app.getBean(beanId, HelpSide.class);
		side.setOwner(owner);
		return side;
	}

	public static void main(String[] args) {
		HelpSide help = get(null, "TFrmTranBG");
		System.out.println(help.getHtml());
	}
}
