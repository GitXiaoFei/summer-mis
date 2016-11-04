package cn.cerc.jmis.core;

import java.io.IOException;

import javax.servlet.ServletException;

public interface IAppLogin {

	boolean checkSecurity(String token) throws IOException, ServletException;

	boolean checkLogin(String userCode, String password) throws ServletException, IOException;

}
