package cn.cerc.jmis.core;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class RequestData {
	private static final Logger log = Logger.getLogger(RequestData.class);
	public static final String appSession_Key = "sid";
	public static final String webclient = "webclient";
	private String sid;
	private String param;
	private String serviceCode;

	public RequestData() {

	}

	public RequestData(HttpServletRequest request) {
		sid = request.getParameter(appSession_Key);
		serviceCode = request.getParameter("class");
		if (serviceCode == null) {
			try {
				serviceCode = request.getPathInfo().substring(1);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException("服务名不能为空！");
			}
		}
		BufferedReader reader;
		try {
			reader = request.getReader();
			StringBuffer params = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				params.append(line);
			}

			param = params.toString();
			if (param != null && param.length() > 1 && param.startsWith("[")) {
				if (param.endsWith("]\r\n")) {
					param = param.substring(1, param.length() - 3);
				} else if (param.endsWith("]")) {
					param = param.substring(1, param.length() - 1);
				}
			}

			if (param != null && param.equals("")) {
				param = null;
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			param = null;
		}
	}

	public String getSid() {
		return sid;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getService() {
		return serviceCode;
	}

	public void setService(String service) {
		this.serviceCode = service;
	}

	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public String getMethod() {
		String[] sl = serviceCode.split("\\.");
		if (sl.length > 1) {
			return sl[1];
		} else {
			return "execute";
		}
	}

	// private static String base64Decode(String param)
	// {
	// if (param == null)
	// {
	// return null;
	// }
	// byte[] bs = null;
	// String result = null;
	// BASE64Decoder decoder = new BASE64Decoder();
	// try
	// {
	// bs = decoder.decodeBuffer(param);
	// result = new String(bs, "UTF-8");
	// }
	// catch (IOException e)
	// {
	// e.printStackTrace();
	// }
	// return result;
	// }

	// public String urlDecode(String text)
	// {
	// if (text == null)
	// {
	// return null;
	// }
	// try
	// {
	// return java.net.URLDecoder.decode(text, "UTF-8");
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// return text;
	// }
	// }

}
