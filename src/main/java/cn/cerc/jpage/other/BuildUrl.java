package cn.cerc.jpage.other;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.UrlRecord;

public interface BuildUrl {
	public void buildUrl(Record ds, UrlRecord url);
}
