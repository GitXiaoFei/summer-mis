package cn.cerc.jmis.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.cerc.jbean.client.LocalService;
import cn.cerc.jbean.core.AppConfig;
import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.tools.IAppLogin;
import cn.cerc.jdb.core.IHandle;
import cn.cerc.jdb.core.Record;
import cn.cerc.jmis.core.RequestData;
import cn.cerc.jmis.form.AbstractForm;

public class AppLoginPage extends AbstractJspPage implements IAppLogin {
	private static final Logger log = Logger.getLogger(AppLoginPage.class);

	@Override
	public void init(IForm form) {
		this.setForm(form);
		AppConfig conf = Application.getConfig();
		this.setJspFile(conf.getJspLoginFile());
		this.add("homePage", conf.getFormWelcome());
		this.add("needVerify", "false");
	}

	@Override
	public boolean checkSecurity(String token) throws IOException, ServletException {
		IForm form = this.getForm();
		String password = null;
		String userCode = null;
		try {
			if (form.getRequest().getParameter("login_usr") != null) {
				userCode = getRequest().getParameter("login_usr");
				password = getRequest().getParameter("login_pwd");
				return checkLogin(userCode, password);
			}
			log.debug(String.format("根据 token(%s) 创建 Session", token));
			IHandle sess = (IHandle) form.getHandle().getProperty(null);
			if (sess.init(token))
				return true;
			if (form.logon())
				return true;
		} catch (Exception e) {
			if (!e.getMessage().contains("</a>")) {
				if (password == null || "".equals(password)) {
					getResponse().sendRedirect("TFrmEasyReg?phone=" + userCode);
					return false;
				}
			}
			this.add("loginMsg", e.getMessage());
		}
		this.execute();
		return false;
	}

	@Override
	public boolean checkLogin(String userCode, String password) throws ServletException, IOException {
		IForm form = this.getForm();
		HttpServletRequest req = this.getRequest();

		log.debug(String.format("校验用户帐号(%s)与密码", userCode));

		// 进行设备首次登记
		String deviceId = form.getClient().getId();
		req.setAttribute("userCode", userCode);
		req.setAttribute("password", password);
		req.setAttribute("needVerify", "false");
		// 如长度大于10表示用手机号码登入
		if (userCode.length() > 10) {
			String oldCode = userCode;
			userCode = getAccountFromTel(form.getHandle(), oldCode);
			log.debug(String.format("将手机号 %s 转化成帐号 %s", oldCode, userCode));
		}

		boolean result = false;
		log.debug(String.format("进行用户帐号(%s)与密码认证", userCode));
		// 进行用户名、密码认证
		LocalService app;
		if (form instanceof AbstractForm)
			app = new LocalService((AbstractForm) form);
		else
			app = new LocalService(form.getHandle());
		app.setService("SvrUserLogin.check");
		if (app.exec("Account_", userCode, "Password_", password, "MachineID_", deviceId)) {
			String sid = app.getDataOut().getHead().getString("SessionID_");
			if (sid != null && !sid.equals("")) {
				log.debug(String.format("认证成功，取得sid(%s)", sid));
				req.setAttribute(RequestData.appSession_Key, sid);
				req.getSession().setAttribute(RequestData.appSession_Key, sid);
				result = true;
			}
		} else {
			// 登陆验证失败，进行判断，手机号为空，则回到登陆页，手机不为空，密码为空，则跳到发送验证码页面
			String mobile = app.getDataOut().getHead().getSafeString("Mobile_");
			if (mobile == null || "".equals(mobile)) {
				log.debug(String.format("用户帐号(%s)与密码认证失败", userCode));
				req.setAttribute("loginMsg", app.getMessage());
				this.execute();
			} else if (password == null || "".equals(password)) {
				getResponse().sendRedirect("TFrmEasyReg?phone=" + mobile);
				return false;
			} else {
				log.debug(String.format("用户帐号(%s)与密码认证失败", userCode));
				req.setAttribute("loginMsg", app.getMessage());
				this.execute();
			}
		}
		return result;
	}

	/**
	 * 根据电话号码返回用户帐号，用于普及版登入
	 * 
	 * @param tel
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private String getAccountFromTel(IHandle handle, String tel) throws ServletException, IOException {
		LocalService app = new LocalService(handle);
		app.setService("SvrUserLogin.getUserCodeByMobile");
		app.getDataIn().getHead().setField("UserCode_", tel);
		if (!app.exec()) {
			Record headOut = app.getDataOut().getHead();
			throw new RuntimeException(headOut.getString("Msg_"));
		} else
			return app.getDataOut().getHead().getString("UserCode_");
	}
}
