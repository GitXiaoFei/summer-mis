package cn.cerc.jmis.tools;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cn.cerc.jpage.common.HtmlText;

public class FieldMark {
	private static ApplicationContext app;
	private static String xmlFile = "classpath:field-mark.xml";

	public static HtmlText get(String beanId) {
		if (app == null)
			app = new FileSystemXmlApplicationContext(xmlFile);
		if (!app.containsBean(beanId))
			return null;
		return app.getBean(beanId, HtmlText.class);
	}
}
