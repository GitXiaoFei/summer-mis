package cn.cerc.jpage.fields;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.other.Expender;
import cn.cerc.jpage.other.SearchItem;

public class ExpendField extends AbstractField implements SearchItem, Expender {
	private boolean search;
	private String hiddenId = "hidden";

	public ExpendField(Component owner) {
		this(owner, "", "_opera_", 5);
	}

	public ExpendField(Component owner, String name, String field) {
		this(owner, name, field, 0);
	}

	public ExpendField(Component owner, String name, String field, int width) {
		super(owner, name, width);
		this.setField(field);
		this.setAlign("center");
		this.setCSSClass_phone("right");
	}

	@Override
	public String getText(Record dataSet) {
		if (dataSet == null)
			return null;
		if (this.search)
			return this.getName();
		if (buildText != null) {
			HtmlWriter html = new HtmlWriter();
			buildText.outputText(dataSet, html);
			return html.toString();
		}
		return String.format("<a href=\"javascript:displaySwitch('%d')\">展开</a>", dataView.getRecNo());
	}

	@Override
	public void output(HtmlWriter html) {
		if (this.search) {
			html.print("<a href=\"javascript:displaySwitch('%s')\">%s</a>", this.getHiddenId(), this.getName());
		} else {
			super.output(html);
		}
	}

	public boolean isSearch() {
		return search;
	}

	@Override
	public void setSearch(boolean search) {
		this.search = search;
	}

	@Override
	public String getHiddenId() {
		if (this.search)
			return hiddenId;
		return "" + dataView.getRecNo();
	}

	public void setHiddenId(String hiddenId) {
		this.hiddenId = hiddenId;
	}

}
