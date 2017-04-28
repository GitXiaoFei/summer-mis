package cn.cerc.jpage.other;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.UrlRecord;
/**
 * 
 * 建立路径
 * @author 陈炙宏
 * @time 2017年4月28日下午6:07:06
 *
 */
public interface BuildUrl {
	public void buildUrl(Record ds, UrlRecord url);
}
