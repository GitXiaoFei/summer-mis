package cn.cerc.jmis.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cn.cerc.jbean.other.BookVersion;
import cn.cerc.jbean.rds.StubHandle;
import cn.cerc.jmis.core.ClientDevice;
import cn.cerc.jmis.core.MenuItem;
import cn.cerc.jmis.tools.SystemMenu;

public class SystemMenuTest {
	// private static final Logger log = Logger.getLogger(SystemMenuTest.class);
	private StubHandle handle = new StubHandle();
	private SystemMenu obj;

	@Before
	public void setUp() {
		obj = new SystemMenu(handle);
	}

	@Test
	@Ignore
	public void test_passItem() {
		MenuItem item = new MenuItem();
		item.setId("TMenuGather");
		item.setParent("");
		item.setSecurity(true);

		String parentMenu = "";
		boolean security = true;
		String device = ClientDevice.device_phone;
		assertTrue("【智能终端】菜单看不到", obj.passItem(item, parentMenu, security, device));
	}

	@Test
	@Ignore(value = "涉及数据库测试，仅在测试区执行")
	public void test_getList() {
		String parentMenu = "";
		boolean security = true;
		String device = ClientDevice.device_phone;
		obj.setCorpType(BookVersion.ctUltimate);
		List<MenuItem> menus = obj.getList(parentMenu, security, device);
		List<String> items = new ArrayList<>();
		for (MenuItem item : menus) {
			if (item.getId().equals("TOrd"))
				assertEquals("在旗舰版中，【批发管理】应叫【销售管理】", "销售管理", item.getCaption());
			items.add(item.getId());
		}
		assertTrue("【基本资料】菜单看不到", items.contains("TBase"));
	}
}
