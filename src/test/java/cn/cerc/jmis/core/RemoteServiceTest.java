package cn.cerc.jmis.core;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cn.cerc.jbean.client.RemoteService;
import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.Record;

public class RemoteServiceTest {
	// private static final Logger log =
	// Logger.getLogger(RemoteServiceTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	@Ignore
	public void test() {
		RemoteService app = new RemoteService();
		// app.setHost("r1.knowall.cn");
		app.setService("SvrUserLogin.check");
		DataSet datain = app.getDataIn();
		Record head = datain.getHead();
		head.setField("Account_", "admin");
		head.setField("Password_", "123456");
		head.setField("MachineID_", "webclient");
		boolean result = app.exec();
		assertTrue(app.getMessage(), result);
	}
}
