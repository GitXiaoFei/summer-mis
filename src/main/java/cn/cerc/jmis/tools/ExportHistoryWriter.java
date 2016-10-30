package cn.cerc.jmis.tools;

import cn.cerc.jbean.other.HistoryLevel;
import cn.cerc.jbean.other.HistoryRecord;
import cn.cerc.jdb.core.IHandle;
import cn.cerc.jexport.excel.HistoryWriter;
import cn.cerc.jexport.excel.Template;

public class ExportHistoryWriter implements HistoryWriter {

	@Override
	public void start(Object handle, Template template) {
	}

	@Override
	public void finish(Object handle, Template template) {
		IHandle appHandle = (IHandle) handle;
		String log = String.format("系统已经为您导出: %s.xls", template.getFileName());
		new HistoryRecord(log).setLevel(HistoryLevel.General).save(appHandle);
	}
}
