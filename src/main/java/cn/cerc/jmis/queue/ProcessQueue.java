package cn.cerc.jmis.queue;

import org.apache.log4j.Logger;

import cn.cerc.jbean.client.LocalService;
import cn.cerc.jbean.core.BookHandle;
import cn.cerc.jbean.core.ServerConfig;
import cn.cerc.jdb.core.TDateTime;
import cn.cerc.jdb.queue.QueueMode;
import cn.cerc.jdb.queue.QueueQuery;
import cn.cerc.jdb.queue.QueueSession;
import cn.cerc.jmis.message.MessageProcess;
import cn.cerc.jmis.task.AbstractTask;
import net.sf.json.JSONObject;

public class ProcessQueue extends AbstractTask {
	private static final Logger log = Logger.getLogger(ProcessQueue.class);

	@Override
	public void execute() throws Exception {
		QueueQuery ds = new QueueQuery(this);
		ds.setQueueMode(QueueMode.recevie);
		ds.add("select * from %s ", QueueSession.defaultQueue);
		ds.open();
		if (!ds.getActive())
			return;
		ds.remove();

		String msgId = ds.getHead().getString("_queueId_");
		String service = ds.getHead().getString("_service_");
		JSONObject content = JSONObject.fromObject(ds.getHead().getString("_content_"));

		BookHandle bh = new BookHandle(this, ds.getHead().getString("_corpNo_"));
		bh.setUserCode(ds.getHead().getString("_userCode_"));
		if ("".equals(bh.getCorpNo()) || "".equals(bh.getUserCode())) {
			log.error("corpNo or userCode is null");
			return;
		}

		LocalService svr = new LocalService(bh);
		svr.setService(service);
		svr.getDataIn().appendDataSet(ds, true);
		LocalService app = new LocalService(bh, "SvrUserMessages.updateAsyncService");
		if (svr.exec()) {
			content.element("processTime", TDateTime.Now());
			content.element("dataOut", svr.getDataOut().getJSON());
			if (!app.exec("msgId", msgId, "process", MessageProcess.ok.ordinal(), "content", content.toString()))
				log.error(app.getMessage());
		} else {
			content.element("processTime", TDateTime.Now());
			content.element("dataOut", svr.getDataOut().getJSON());
			if (!app.exec("msgId", msgId, "process", MessageProcess.error.ordinal(), "content", content.toString()))
				log.error(app.getMessage());
		}
	}

	@Override
	public void run() {
		if (ServerConfig.enableTaskService())
			super.run();
	}

	public static void main(String[] args) {
		ProcessQueue obj = new ProcessQueue();
		obj.run();
	}
}
