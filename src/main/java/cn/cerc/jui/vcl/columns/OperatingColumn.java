package cn.cerc.jui.vcl.columns;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;

/**
 * 操作列,提供删除和修改按钮
 * 
 * @author rick_zhou 2016年11月2日 上午11:29:37
 */
public class OperatingColumn extends AbstractColumn {

	private String removeUrl;
	private String modifyUrl;

	public OperatingColumn(Component owner) {
		super(owner);
	}

	public OperatingColumn(Component owner, String field, String title) {
		super(owner, field, title);
	}

	public OperatingColumn(Component owner, String field, String title, String modifyUrl, String removeUrl) {
		super(owner, field, title);
		this.removeUrl = removeUrl;
		this.modifyUrl = modifyUrl;
	}

	public void add(String value) {
		this.getGrid().getCurrent().addData(this, value);
	}

	@Override
	public String format(Object value) {
		StringBuffer sb = new StringBuffer();
		sb.append("<td>");
		if (value instanceof Record) {
			sb.append(String.format(
					"<input class='button addBtn'  type='button' onclick=\"javascript:window.location.href='%s?UID_=%s'\" value='修改' />",
					modifyUrl, super.format(value)));
			sb.append(String.format(
					"<input class='button editBtn' type='button' onclick=\"fnRemoveRow('%s','%s')\" value='删除' />",
					removeUrl, super.format(value)));
		} else {
			sb.append(value);
		}
		sb.append("</td>");
		return sb.toString();
	}

	public String getModifyUrl() {
		return modifyUrl;
	}

	public void setModifyUrl(String modifyUrl) {
		this.modifyUrl = modifyUrl;
	}

	public String getRemoveUrl() {
		return removeUrl;
	}

	public void setRemoveUrl(String removeUrl) {
		this.removeUrl = removeUrl;
	}

}
