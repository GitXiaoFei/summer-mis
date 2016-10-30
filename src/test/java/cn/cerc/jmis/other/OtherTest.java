package cn.cerc.jmis.other;

import java.net.URISyntaxException;

import cn.cerc.jmis.core.MenuFactory;

public class OtherTest {
	public static void main(String[] args) throws URISyntaxException {
		String filepath = MenuFactory.class.getClassLoader().getResource("").getPath();
		System.out.println(filepath);
		String filepath2 = MenuFactory.class.getClassLoader().getResource("").toURI().getPath();
		System.out.println(filepath2);
	}
}
