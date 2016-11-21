package cn.cerc.jpage.common;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.HtmlWriter;

public interface BuildText {
	public void outputText(Record ds, HtmlWriter html);
}
