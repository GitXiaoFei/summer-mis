package cn.cerc.jmis.task;

import org.apache.log4j.Logger;

import cn.cerc.jbean.core.AbstractHandle;
import cn.cerc.jbean.core.AppHandle;
import cn.cerc.jbean.core.Application;

public abstract class AbstractTask extends AbstractHandle implements Runnable {
	//加载日志
	private static final Logger log = Logger.getLogger(AbstractTask.class);
	//描述
	private String describe;
	private int interval;
	//时间
	private String time = "";

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * 外部执行入口
	 */
	public void run() {
		try (AppHandle handle = new AppHandle()) {
			this.setHandle(handle);
			handle.setProperty(Application.userCode, "admin");
			this.execute();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	// 具体业务逻辑代码
	public abstract void execute() throws Exception;
}
