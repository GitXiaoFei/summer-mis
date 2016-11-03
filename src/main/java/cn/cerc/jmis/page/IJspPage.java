package cn.cerc.jmis.page;

import cn.cerc.jbean.form.IForm;
import cn.cerc.jbean.form.IPage;

public interface IJspPage extends IPage {
	// 返回所在的Form
	public IForm getForm();

	// 输出到支持Jstl的jsp文件
	public void add(String id, Object value);

	public String getMessage();

	public void setMessage(String message);

	// 设置jspFile
	public String getJspFile();

	// 取得jspFile
	public void setJspFile(String jspFile);

	// 返回带设备码的jsp文件
	public String getViewFile();
}
