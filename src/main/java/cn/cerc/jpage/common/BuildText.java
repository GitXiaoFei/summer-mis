package cn.cerc.jpage.common;

import cn.cerc.jdb.core.Record;

public interface BuildText {
	public void outputText(Record ds, HtmlWriter html);
}
