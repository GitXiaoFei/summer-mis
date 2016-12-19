package cn.cerc.jpage.other;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.HtmlWriter;

public interface BuildText {
	public void outputText(Record ds, HtmlWriter html);
}
