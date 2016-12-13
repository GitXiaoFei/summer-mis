package cn.cerc.jmis.core;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public interface IFormFilter {
	boolean doFilter(HttpServletResponse resp, String formId, String funcCode) throws IOException;
}
