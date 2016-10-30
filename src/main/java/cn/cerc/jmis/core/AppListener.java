package cn.cerc.jmis.core;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

@WebListener
public class AppListener implements HttpSessionListener {
	private static final Logger log = Logger.getLogger(AppListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent hse) {
		ServletContext context = hse.getSession().getServletContext();
		Integer count = (Integer) context.getAttribute("count");
		count = count == null ? 1 : count + 1;
		context.setAttribute("count", count);
		outputMessage(hse.getSession(), "Created", count);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent hse) {
		ServletContext context = hse.getSession().getServletContext();
		Integer count = (Integer) context.getAttribute("count");
		count = count == null ? 0 : count - 1;
		context.setAttribute("count", count);
		outputMessage(hse.getSession(), "Destroyed", count);
	}

	private void outputMessage(HttpSession session, String value, int count) {
		log.debug(String.format("Session %s %s, count: %d", session.getId(), value, count));
	}
}
