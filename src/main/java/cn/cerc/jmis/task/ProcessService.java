package cn.cerc.jmis.task;

import java.util.Calendar;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.core.ServerConfig;
import cn.cerc.jbean.other.BufferType;
import cn.cerc.jbean.rds.StubHandle;
import cn.cerc.jdb.cache.IMemcache;
import cn.cerc.jdb.core.IHandle;
import cn.cerc.jdb.core.TDateTime;

public class ProcessService extends TimerTask {
	private static final Logger log = Logger.getLogger(ProcessService.class);
	private static ApplicationContext taskApp;
	private static String taskFile = "classpath:app-tasks.xml";
	private static boolean isRunning = false;
	// 晚上12点执行，也即0点开始执行
	private static final int C_SCHEDULE_HOUR = 0;
	private static String lock;

	// 运行环境
	private ServletContext context = null;

	public static AbstractTask getTask(IHandle handle, String beanId) {
		if (taskApp == null)
			taskApp = new FileSystemXmlApplicationContext(taskFile);
		if (!taskApp.containsBean(beanId))
			return null;

		AbstractTask result = taskApp.getBean(beanId, AbstractTask.class);
		if (result != null)
			result.setHandle(handle);
		return result;
	}

	public ProcessService(ServletContext context) {
		this.context = context;
	}

	// 循环反复执行
	@Override
	public void run() {
		Calendar c = Calendar.getInstance();
		if (!isRunning) {
			isRunning = true;
			if (C_SCHEDULE_HOUR == c.get(Calendar.HOUR_OF_DAY)) {
				context.log("开始执行定时任务");
				try {
					report();
				} catch (Exception e) {
					e.printStackTrace();
				}
				context.log("定时任务执行结束");
			} else if (ServerConfig.enableTaskService()) {
				try {
					StubHandle handle = new StubHandle();
					runTask(handle);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			isRunning = false;
		} else {
			context.log("上一次任务执行还未结束");
		}
	}

	// 每天凌晨开始执行报表或回算任务
	private void report() {
		log.info("report: " + TDateTime.Now());
	}

	private void runTask(IHandle handle) {
		// 同一秒内，不允许执行2个及以上任务
		String str = TDateTime.Now().getTime();
		if (str.equals(lock))
			return;

		if (taskApp == null)
			taskApp = new FileSystemXmlApplicationContext(taskFile);

		lock = str;
		for (String beanId : taskApp.getBeanDefinitionNames()) {
			AbstractTask task = getTask(handle, beanId);
			try {
				String curTime = TDateTime.Now().getTime().substring(0, 5);
				if (!"".equals(task.getTime()) && !task.getTime().equals(curTime))
					continue;

				int timeOut = task.getInterval();
				String buffKey = String.format("%d.%s.%s", BufferType.getObject.ordinal(), this.getClass().getName(),
						task.getClass().getName());
				IMemcache buff = Application.getMemcache();
				if (buff.get(buffKey) != null)
					continue;

				// 标识为已执行
				buff.set(buffKey, "ok", timeOut);

				if (task.getInterval() > 1)
					log.info("execute " + task.getClass().getName());

				task.execute();
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}

		}
	}
}
