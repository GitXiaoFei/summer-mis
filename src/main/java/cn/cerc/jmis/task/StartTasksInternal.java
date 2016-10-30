package cn.cerc.jmis.task;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.cerc.jbean.core.ServerConfig;

//使用内 部驱动定时任务
public class StartTasksInternal implements ServletContextListener {
	private static final int step = 500;
	private Timer timer = null;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		if (ServerConfig.enableTaskService()) {
			timer = new Timer(true);
			event.getServletContext().log("定时器已启动");
			// 3秒后开始启动，5*1000表示每隔5秒执行任务
			timer.schedule(new ProcessService(event.getServletContext()), 3 * 1000, step);
			event.getServletContext().log("已经添加任务");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if (timer != null) {
			timer.cancel();
			event.getServletContext().log("定时器销毁");
		}
	}
}
