package cn.cerc.jmis.queue;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import cn.cerc.jbean.rds.StubHandle;

public class AsyncServiceTest {

	@Test
	@Ignore
	public void test_send_get() {
		StubHandle handle = new StubHandle();
		AsyncService app = new AsyncService(handle);
		app.setService("TAppCreditLine.calCusCreditLimit");
		// app.setTimer(TDateTime.Now().getTime());
		app.getDataIn().getHead().setField("UserCode_", handle.getUserCode());
		app.setSubject("回算信用额度");
		assertTrue("发送消息失败", app.exec());
	}
}
