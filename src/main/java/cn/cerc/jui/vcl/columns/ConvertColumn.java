package cn.cerc.jui.vcl.columns;

import java.util.HashMap;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;

/**
 * 值转换列
 * 
 * @author rick_zhou 2016年11月1日 上午11:25:48
 */
public class ConvertColumn extends AbstractColumn {

	private HashMap<String, String> maping = new HashMap<String, String>(8);
	private String normalValue;

	public ConvertColumn(Component owner) {
		super(owner);
	}

	public ConvertColumn(Component owner, String field, String title) {
		super(owner, field, title);
	}

	public ConvertColumn(Component owner, String field, String title, String... valueMaping) {
		super(owner, field, title);
		if (valueMaping == null || valueMaping.length == 0) {
			return;
		}
		int length = 0;
		if (valueMaping.length % 2 == 1) {// 默认值
			this.normalValue = valueMaping[valueMaping.length - 1];
			length = valueMaping.length - 1;
		}
		for (int i = 0; i < length; i++) {
			if (i % 2 == 1) {
				this.maping.put(valueMaping[i - 1], valueMaping[i]);
			}
		}

	}

	public void add(String value) {
		this.getGrid().getCurrent().add(this, value);
	}

	public void addMapingItems(String value, String toValue) {
		this.maping.put(value, toValue);
	}

	public void setNormalValue(String normalValue) {
		this.normalValue = normalValue;
	}

	@Override
	public String format(Object value) {
		StringBuffer sb = new StringBuffer();
		sb.append("<td>");
		if (value instanceof Record) {
			String mapingValue = maping.get(super.format(value));// 取值
			sb.append(mapingValue == null ? this.normalValue : mapingValue);// 取默认值
		} else {
			sb.append(value);
		}
		sb.append("</td>");
		return sb.toString();
	}
}
