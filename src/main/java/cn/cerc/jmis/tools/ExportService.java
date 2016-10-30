package cn.cerc.jmis.tools;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import jxl.write.WriteException;
import cn.cerc.jbean.client.LocalService;
import cn.cerc.jbean.other.BufferType;
import cn.cerc.jbean.other.MemoryBuffer;
import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.IHandle;
import cn.cerc.jexport.excel.AccreditException;
import cn.cerc.jexport.excel.ExportExcel;
import cn.cerc.jmis.form.AbstractForm;

public class ExportService extends ExportExcel {
	private String service;
	private String exportKey;

	public ExportService(AbstractForm owner) {
		super(owner.getResponse());
		this.setHandle(owner);
		HttpServletRequest request = owner.getRequest();
		service = request.getParameter("service");
		exportKey = request.getParameter("exportKey");
	}

	public void export() throws WriteException, IOException, AccreditException {
		if (service == null || "".equals(service))
			throw new RuntimeException("错误的调用：service is null");
		if (exportKey == null || "".equals(exportKey))
			throw new RuntimeException("错误的调用：exportKey is null");
		IHandle handle = (IHandle) this.getHandle();
		LocalService app = new LocalService(handle);
		app.setService(service);
		try (MemoryBuffer buff = new MemoryBuffer(BufferType.getExportKey, handle.getUserCode(), exportKey)) {
			app.getDataIn().close();
			app.getDataIn().setJSON(buff.getString("data"));
		}
		if (!app.exec()) {
			this.export(app.getMessage());
			return;
		}

		DataSet dataOut = app.getDataOut();
		// 对分类进行处理
		dataOut.first();
		while (dataOut.fetch()) {
			if (dataOut.getBoolean("IsType_"))
				dataOut.delete();
		}
		this.getTemplate().setDataSet(dataOut);
		super.export();
	}
}
