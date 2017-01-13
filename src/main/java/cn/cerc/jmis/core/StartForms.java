package cn.cerc.jmis.core;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import cn.cerc.jbean.client.LocalService;
import cn.cerc.jbean.core.AppConfig;
import cn.cerc.jbean.core.AppHandle;
import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.core.PageException;
import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IMenu;
import cn.cerc.jbean.form.IPage;
import cn.cerc.jbean.other.BufferType;
import cn.cerc.jbean.other.HistoryLevel;
import cn.cerc.jbean.other.HistoryRecord;
import cn.cerc.jbean.other.MemoryBuffer;
import cn.cerc.jbean.tools.IAppLogin;
import cn.cerc.jdb.core.Record;
import cn.cerc.jdb.core.TDate;
import cn.cerc.jmis.form.Webpage;
import cn.cerc.jmis.page.ErrorPage;
import cn.cerc.jmis.page.JspPage;
import cn.cerc.jmis.page.RedirectPage;

public class StartForms implements Filter {
	private static final Logger log = Logger.getLogger(StartForms.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String uri = req.getRequestURI();

		// 遇到图像文件直接输出
		if (uri.endsWith(".css") || uri.endsWith(".jpg") || uri.endsWith(".gif") || uri.endsWith(".png")
				|| uri.endsWith(".bmp") || uri.endsWith(".js") || uri.endsWith(".mp3") || uri.endsWith(".icon")
				|| uri.endsWith(".apk") || uri.endsWith(".exe") || uri.endsWith(".jsp") || uri.endsWith(".htm")
				|| uri.endsWith(".html") || uri.endsWith(".manifest")) {
			chain.doFilter(req, resp);
			return;
		}

		log.info(uri);
		String childCode = getRequestForm(req);
		if (childCode == null) {
			req.setAttribute("message", "无效的请求：" + childCode);
			req.getRequestDispatcher(Application.getConfig().getJspErrorFile()).forward(req, resp);
			return;
		}

		String[] params = childCode.split("\\.");
		String formId = params[0];
		String funcCode = params.length == 1 ? "execute" : params[1];

		req.setAttribute("logon", false);

		// 验证菜单是否启停
		if (Application.containsBean("AppFormFilter")) {
			IFormFilter ff = Application.getBean("AppFormFilter", IFormFilter.class);
			if (ff != null) {
				if (ff.doFilter(resp, formId, funcCode))
					return;
			}
		}

		IForm form = null;
		try {
			form = Application.getForm(req, resp, formId);
			if (form == null) {
				req.setAttribute("message", "error servlet:" + req.getServletPath());
				AppConfig conf = Application.getConfig();
				req.getRequestDispatcher(conf.getJspErrorFile()).forward(req, resp);
				return;
			}

			// 设备讯息
			ClientDevice info = new ClientDevice(form);
			info.setRequest(req);
			req.setAttribute("_showMenu_", !ClientDevice.device_ee.equals(info.getDevice()));
			form.setClient(info);
			// 查找菜单定义
			IMenu menu = form.getMenu();
			if (menu == null) {
				IAppMenus menus = Application.getBean("AppMenus", IAppMenus.class);
				if (menus != null)
					form.setMenu(menus.getItem(formId));
			}

			// 建立数据库资源
			try (AppHandle handle = new AppHandle()) {
				try {
					handle.setProperty(Application.sessionId, req.getSession().getId());
					form.setHandle(handle);
					log.debug("进行安全检查，若未登录则显示登录对话框");
					IAppLogin page = Application.getAppLogin(form);
					if (page.checkSecurity(info.getSid())) {
						String corpNo = handle.getCorpNo();
						if (null != corpNo && !"".equals(corpNo)) {
							String tempStr = String.format("调用菜单: %s(%s), 用户：%s", form.getTitle(), formId,
									handle.getUserName());
							new HistoryRecord(tempStr).setLevel(HistoryLevel.General).save(handle);
						}
						// 进行维护检查，在每月的最后一天晚上11点到下个月的第一天早上5点，不允许使用系统
						if (checkEnableTime())
							call(form, funcCode);
					}
				} catch (Exception e) {
					Throwable err = e.getCause();
					if (err == null)
						err = e;
					req.setAttribute("msg", err.getMessage());
					ErrorPage opera = new ErrorPage(form, err);
					opera.execute();
				}
			}
		} catch (Exception e) {
			log.error(e);
			req.setAttribute("message", e.getMessage());
			AppConfig conf = Application.getConfig();
			req.getRequestDispatcher(conf.getJspErrorFile()).forward(req, resp);
			return;
		}
	}

	private boolean checkEnableTime() {
		Calendar cal = Calendar.getInstance();
		// 月底最后一天
		if (TDate.Today().compareTo(TDate.Today().monthEof()) == 0) {
			if (cal.get(Calendar.HOUR_OF_DAY) >= 23)
				throw new RuntimeException("系统现正在进行月初例行维护，维护时间为月底晚上23点至月初早上5点，请您在这段时间内不要使用系统，谢谢！");
		}
		// 月初第一天
		if (TDate.Today().compareTo(TDate.Today().monthBof()) == 0)
			if (cal.get(Calendar.HOUR_OF_DAY) < 5)
				throw new RuntimeException("系统现正在进行月初例行维护，维护时间为月底晚上23点至月初早上5点，请您在这段时间内不要使用系统，谢谢！");
		return true;
	}

	// 是否在当前设备使用此菜单，如：检验此设备是否需要设备验证码
	private boolean passDevice(IForm form) {
		String deviceId = form.getClient().getId();
		String verifyCode = form.getRequest().getParameter("verifyCode");
		log.debug(String.format("进行设备认证, deviceId=%s", deviceId));
		String userId = (String) form.getHandle().getProperty("UserID");
		try (MemoryBuffer buff = new MemoryBuffer(BufferType.getSessionInfo, userId, deviceId)) {
			if (!buff.isNull()) {
				if (buff.getBoolean("VerifyMachine")) {
					log.debug("已经认证过，跳过认证");
					return true;
				}
			}

			boolean result = false;
			LocalService app = new LocalService(form.getHandle());
			app.setService("SvrUserLogin.verifyMachine");
			app.getDataIn().getHead().setField("deviceId", deviceId);
			if (verifyCode != null && !"".equals(verifyCode))
				app.getDataIn().getHead().setField("verifyCode", verifyCode);

			if (app.exec())
				result = true;
			else {
				int used = app.getDataOut().getHead().getInt("Used_");
				if (used == 1)
					result = true;
				else
					form.setParam("message", app.getMessage());
			}
			if (result)
				buff.setField("VerifyMachine", true);
			return result;
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	private final void call(IForm form, String funcCode) throws ServletException, IOException {
		HttpServletResponse response = form.getResponse();
		HttpServletRequest request = form.getRequest();
		if ("excel".equals(funcCode)) {
			response.setContentType("application/vnd.ms-excel; charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename=excel.csv");
		} else
			response.setContentType("text/html;charset=UTF-8");

		Object pageOutput = "";
		String sid = request.getParameter(RequestData.appSession_Key);
		if (sid == null || sid.equals(""))
			sid = request.getSession().getId();

		Method method = null;
		long startTime = System.currentTimeMillis();
		try {
			String CLIENTVER = request.getParameter("CLIENTVER");
			if (CLIENTVER != null)
				request.getSession().setAttribute("CLIENTVER", CLIENTVER);

			// 是否拥有此菜单调用权限
			if ("true".equals(form.getParam("security", "false"))) {
				if (!Application.getPassport(form.getHandle()).passProc(form.getParam("versions", null),
						form.getParam("procCode", null)))
					throw new RuntimeException("对不起，您没有权限执行此功能！");
			}
			// 增加91100128账号跳过设备认证的判断，用于苹果公司测试，后需删除此判断
			if ("91100128".equals(request.getParameter("login_usr"))) {
				try {
					if (form.getClient().isPhone()) {
						try {
							method = form.getClass().getMethod(funcCode + "_phone");
						} catch (NoSuchMethodException e) {
							method = form.getClass().getMethod(funcCode);
						}
					} else
						method = form.getClass().getMethod(funcCode);
					pageOutput = method.invoke(form);
				} catch (PageException e) {
					form.setParam("message", e.getMessage());
					pageOutput = e.getViewFile();
				}
			} else {
				// 检验此设备是否需要设备验证码
				if (form.getHandle().getProperty("UserID") == null || form.passDevice() || passDevice(form))
					try {
						if (form.getClient().isPhone()) {
							try {
								method = form.getClass().getMethod(funcCode + "_phone");
							} catch (NoSuchMethodException e) {
								method = form.getClass().getMethod(funcCode);
							}
						} else
							method = form.getClass().getMethod(funcCode);
						pageOutput = method.invoke(form);
					} catch (PageException e) {
						form.setParam("message", e.getMessage());
						pageOutput = e.getViewFile();
					}
				else {
					log.debug("没有进行认证过，跳转到设备认证页面");
					pageOutput = new RedirectPage(form, Application.getConfig().getFormVerifyDevice());
				}
			}

			// FIXME: 此处代码用于ee关闭问题，后续改进
			if (funcCode.equals("execute")) {
				if (ClientDevice.device_ee.equals(form.getClient().getDevice()))
					request.getSession().setAttribute(form.getClass().getName(), true);
			}

			// 处理返回值
			if (pageOutput != null) {
				if (pageOutput instanceof IPage) {
					IPage output = (IPage) pageOutput;
					output.execute();
				} else {
					log.warn(String.format("%s pageOutput is not IPage: %s", funcCode, pageOutput));
					JspPage output = new JspPage(form);
					output.setJspFile((String) pageOutput);
					output.execute();
				}
			}
		} catch (Exception e) {
			Throwable err = e.getCause();
			if (err == null)
				err = e;
			ErrorPage opera = new ErrorPage(form, err);
			opera.execute();
		} finally {
			if (method != null) {
				long timeout = 1000;
				Webpage webpage = method.getAnnotation(Webpage.class);
				if (webpage != null)
					timeout = webpage.timeout();

				long totalTime = System.currentTimeMillis() - startTime;
				if (totalTime > timeout) {
					String tmp[] = form.getClass().getName().split("\\.");
					String service = tmp[tmp.length - 1] + "." + funcCode;
					saveFormTimeout(form, service, totalTime);
				}
			}
		}
	}

	private void saveFormTimeout(IForm form, String pageCode, long totalTime) {
		String dataIn = new Gson().toJson(form.getRequest().getParameterMap());
		if (dataIn.length() > 60000)
			dataIn = dataIn.substring(0, 60000);
		LocalService ser = new LocalService(form.getHandle(), "SvrFormTimeout.save");
		Record head = ser.getDataIn().getHead();
		head.setField("pageCode", pageCode);
		head.setField("dataIn", dataIn);
		head.setField("tickCount", totalTime);
		ser.exec();
	}

	private String getRequestForm(HttpServletRequest req) {
		String url = null;
		String args[] = req.getServletPath().split("/");
		if (args.length == 2 || args.length == 3) {
			if (args[0].equals("") && !args[1].equals("")) {
				if (args.length == 3)
					url = args[2];
				else {
					String sid = (String) req.getAttribute(RequestData.appSession_Key);
					AppConfig conf = Application.getConfig();
					if (sid != null && !"".equals(sid))
						url = conf.getFormDefault();
					else
						url = conf.getFormWelcome();
				}
			}
		}
		return url;
	}

}
