package cn.cerc.jmis.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import cn.cerc.jbean.core.DataValidateException;
import cn.cerc.jbean.core.ServiceException;
import cn.cerc.jbean.other.BufferType;
import cn.cerc.jbean.other.MemoryBuffer;
import cn.cerc.jbean.other.SystemTable;
import cn.cerc.jbean.rds.StubHandle;
import cn.cerc.jdb.core.Record;
import cn.cerc.jdb.mysql.SqlQuery;

public class SvrUserLoginTest {
	// private static final Logger log = Logger.getLogger(TAppLoginTest.class);

	// 测试帐号找不到时的提示
	@Test(expected = ServiceException.class)
	@Ignore
	public void testCheck1() throws Exception {
		String corpNo = "911001";
		String userCode = "91100101";
		StubHandle handle = new StubHandle(corpNo, userCode);
		SvrUserLogin app = new SvrUserLogin();
		app.init(handle);
		Record headIn = app.getDataIn().getHead();
		headIn.setField("Account_", userCode);
		assertFalse(app.Check());
	}

	@Test
	@Ignore
	public void testCheck2() throws SecurityCheckException {
		StubHandle handle = new StubHandle();
		String userCode = handle.getUserCode();
		SvrUserLogin app = new SvrUserLogin();

		app.init(handle);
		Record headIn = app.getDataIn().getHead();
		headIn.setField("Account_", userCode);
		boolean ok = app.Check();
		assertEquals(app.getMessage(), "您的登录密码错误，禁止登录！");
		assertThat(app.getDataOut().getJSON(), is("{\"head\":{\"errorNo\":0}}"));
		assertTrue(!ok);

	}

	@Test
	@Ignore
	public void testCheck3() throws Exception {
		String corpNo = "911001";
		String userCode = "9110010001";
		StubHandle handle = new StubHandle(corpNo, userCode);
		SvrUserLogin app = new SvrUserLogin();
		app.init(handle);
		Record headIn = app.getDataIn().getHead();
		headIn.setField("Account_", userCode);
		boolean ok = app.Check();
		assertEquals(app.getMessage(), "您的登录密码错误，禁止登录！");
		assertThat(app.getDataOut().getJSON(), is("{\"head\":{\"errorNo\":0}}"));
		assertTrue(!ok);
	}

	@Test
	@Ignore(value = "此处用于测试在5分钟内不允许重复申请验证码，耗时很长")
	public void test_sendVerifyCode() throws InterruptedException, DataValidateException {
		String corpNo = "911001";
		String userCode = "91100123";
		String deviceId = "TEST";
		try (StubHandle handle = new StubHandle(corpNo, userCode)) {
			// 清空缓存
			try (MemoryBuffer buff = new MemoryBuffer(BufferType.getObject, handle.getUserCode(),
					SvrUserLogin.class.getName(), "sendVerifyCode")) {
				buff.clear();
			}
			// 检查验证码是否存在
			SqlQuery ds = new SqlQuery(handle);

			ds.add("select * from %s", SystemTable.get(SystemTable.getDeviceVerify));
			ds.add("where CorpNo_='%s'", corpNo);
			ds.add("and UserCode_='%s'", userCode);
			ds.add("and MachineCode_='%s'", deviceId);
			ds.open();
			String msg = String.format("帐号 %s 验证码 %s 不存在，无法完成测试", userCode, deviceId);
			assertThat(msg, ds.eof(), is(false));

			SvrUserLogin app = new SvrUserLogin();

			app.init(handle);
			app.getDataIn().getHead().setField("deviceId", deviceId);
			assertThat(app.sendVerifyCode(), is(true));
			Thread.sleep(1000 * 30);
			try {
				app.sendVerifyCode();
				assertThat("此处不应该执行到", false, is(true));
			} catch (Exception e) {
				e.printStackTrace();
				assertThat(e.getMessage().indexOf("5 分钟") > 0, is(true));
			}
			Thread.sleep(1000 * 60 * SvrUserLogin.VerifyCodeTimeout);
			assertThat(app.sendVerifyCode(), is(true));
		}
	}
}
