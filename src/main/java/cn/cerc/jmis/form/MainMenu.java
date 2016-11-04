package cn.cerc.jmis.form;

import static cn.cerc.jmis.core.ClientDevice.device_phone;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jbean.core.Application;
import cn.cerc.jmis.page.AbstractJspPage;
import cn.cerc.jpage.other.Url_Record;

public class MainMenu {
	// 页面标题
	private String pageTitle = null;
	// 退出
	private Url_Record exitPage = null;
	// 首页
	private Url_Record homePage;
	// 左边菜单
	private List<Url_Record> leftMenus = new ArrayList<>();
	// 右边菜单
	private List<Url_Record> rightMenus = new ArrayList<>();

	public MainMenu() {
		homePage = new Url_Record(Application.getConfig().getFormDefault(), "<img src=\"images/Home.png\"/>");
		leftMenus.add(homePage);
	}

	public void finish(AbstractJspPage page, boolean logon) {
		String device = page.getForm().getClient().getDevice();
		// 刷新
		if (this.pageTitle != null) {
			leftMenus.add(new Url_Record("javascript:location.reload()", this.pageTitle));
		}

		// 设置退出
		String tmp = (String) page.getForm().getRequest().getAttribute("exitPage");
		if (exitPage != null && tmp != null && !tmp.equals(""))
			exitPage.setCaption("<=").setUrl(tmp);

		if (exitPage != null)
			rightMenus.add(exitPage);

		if (leftMenus.size() > 0) {
			if (device_phone.equals(device) && leftMenus.size() > 2) {
				List<Url_Record> tmp1 = new ArrayList<>();
				tmp1.add(leftMenus.get(0));
				tmp1.add(leftMenus.get(leftMenus.size() - 1));
				page.add("barMenus", tmp1);
			} else {
				page.add("barMenus", leftMenus);
			}
		}

		if (rightMenus.size() > 0)
			page.add("subMenus", rightMenus);
	}

	public void addLeftMenu(String url, String name) {
		leftMenus.add(new Url_Record(url, name));
	}

	public void addRightMenu(String url, String name) {
		rightMenus.add(new Url_Record(url, name));
	}

	public void setExitUrl(String url) {
		if (exitPage == null)
			exitPage = new Url_Record();
		exitPage.setCaption("<img src=\"images/return.png\"/>");
		exitPage.setUrl(url);
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public Url_Record getHomePage() {
		return homePage;
	}

	public List<Url_Record> getRightMenus() {
		return rightMenus;
	}
}
