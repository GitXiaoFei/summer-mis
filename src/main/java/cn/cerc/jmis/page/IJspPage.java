package cn.cerc.jmis.page;

import cn.cerc.jbean.form.IPage;
import cn.cerc.jbean.other.MemoryBuffer;
import cn.cerc.jdb.other.utils;

public interface IJspPage extends IPage {
	// 输出到支持Jstl的jsp文件
	public void add(String id, Object value);

	public String getMessage();

	public void setMessage(String message);

	// 返回带设备码的jsp文件
	public String getViewFile();

	// 从请求或缓存读取数据
	default public String getValue(MemoryBuffer buff, String reqKey) {
		String result = getRequest().getParameter(reqKey);
		if (result == null) {
			String val = buff.getString(reqKey).replace("{}", "");
			if (utils.isNumeric(val) && val.endsWith(".0"))
				result = val.substring(0, val.length() - 2);
			else
				result = val;
		} else {
			result = result.trim();
			buff.setField(reqKey, result);
		}
		this.add(reqKey, result);
		return result;
	}

}
