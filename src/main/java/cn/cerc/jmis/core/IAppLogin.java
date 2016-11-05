package cn.cerc.jmis.core;

import java.io.IOException;

import javax.servlet.ServletException;

import cn.cerc.jbean.form.IForm;

public interface IAppLogin {

	void init(IForm form);
	
	boolean checkSecurity(String token) throws IOException, ServletException;

	boolean checkLogin(String userCode, String password) throws IOException, ServletException;

}
