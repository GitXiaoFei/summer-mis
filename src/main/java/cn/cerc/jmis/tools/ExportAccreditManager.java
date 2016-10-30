package cn.cerc.jmis.tools;

import static cn.cerc.jbean.other.UserOptions.UserOptionEnabled;

import cn.cerc.jdb.core.IHandle;
import cn.cerc.jexport.excel.AccreditManager;

public class ExportAccreditManager implements AccreditManager {
	private String securityCode;
	private String describe;

	@Override
	public boolean isPass(Object handle) {
		if (securityCode == null)
			throw new RuntimeException("securityCode is null");
		IHandle appHandle = (IHandle) handle;
		return UserOptionEnabled(appHandle, securityCode);
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
