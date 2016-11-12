package cn.cerc.jpage.grid.extjs;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jmis.page.AbstractJspPage;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.fields.ExpendField;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.grid.Grid;

public class ExtGrid extends Grid {
	private String title;
	private String postUrl;
	private String onPostSuccess;

	public ExtGrid(AbstractJspPage page) {
		super(page);
		page.addStyleFile("/ext4/resources/css/ext-all.css");
		page.addScriptFile("/ext4/ext-all.js");
		// Page.addScriptFile("/ext4/ext-all-debug.js");
		page.addScriptFile("/ext4/ext-lang-zh_CN.js");
	}

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	@Override
	public void output(HtmlWriter html) {
		outputGrid(html);

		if (form != null)
			form.output(html);

		html.println("<div id=\"%s\"></div>", this.getId());

		if (form != null)
			html.println("</form>");
	}

	@Override
	public void outputGrid(HtmlWriter html) {
		html.println("");
		html.println("<script>");
		html.print("Ext.require([");
		html.print("'Ext.grid.*',");
		html.print("'Ext.data.*',");
		html.print("'Ext.util.*',");
		html.print("'Ext.state.*'");
		html.println("]);");
		html.println("");

		html.println("var grid;");
		html.println("var store;");

		// 数值为负显示为红色,正数显示为绿色
		// html.println("function numberChange(val) {");
		// html.println("if (val > 0) {");
		// html.println("return '<span style=\"color:green;\">' + val +
		// '</span>';");
		// html.println("} else if (val < 0) {");
		// html.println("return '<span style=\"color:red;\">' + val +
		// '</span>';");
		// html.println("}");
		// html.println("return val;");
		// html.println("}");

		html.println("Ext.onReady(function() {");
		html.println("Ext.QuickTips.init();");
		html.println("var myData=%s;", new GridData().setGrid(this).toString());
		html.println("store=Ext.create('Ext.data.ArrayStore', {");
		html.println("fields:%s,", new GridFields().setGrid(this).toString());
		html.println("data: myData});");
		html.println("var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {clicksToEdit: 1});");
		if (this.isMoreContext()) {
			// 定义展开的内容
			List<AbstractField> items = new ArrayList<>();
			for (AbstractField field : this.getFields()) {
				if (field.getExpender() != null)
					items.add(field);
			}
			html.println("var tpl = new Ext.XTemplate(");
			for (int i = 0; i < items.size(); i++) {
				AbstractField field = items.get(i);
				html.print("'<b>%s: </b>{%s} &nbsp;'", field.getName(), field.getField());
				if (i < items.size() - 1)
					html.println(",");
			}
			html.println(")");
			html.println("var rowExpander = Ext.create('Ext.grid.plugin.RowExpander',{");
			html.println("rowBodyTpl:tpl,");
			html.println("expandOnEnter: false");
			html.println("}),");
		}

		html.println("grid = Ext.create('Ext.grid.Panel', {");
		html.println("store: store,");
		html.println("columnLines: true,");
		html.println("columns: %s,", new GridColumns().setGrid(this).toString());
		html.print("selModel: {selType: 'cellmodel'},");
		html.print("height: extGridHeight,");
		html.print("width: '100%',");
		if (this.title != null)
			html.print("title: '%s',", this.title);
		html.println("renderTo: '%s',", this.getId());
		html.print("viewConfig: {stripeRows: true},");
		if (this.isMoreContext())
			html.println("plugins: [cellEditing,rowExpander],");
		else
			html.println("plugins: [cellEditing],");
		// html.println("tbar: [{");
		// html.println("text: \"保存\",");
		// html.println("handler: function() {");
		// html.println("var modified = grid.store.getModifiedRecords();");
		// html.println("updateData(modified)");
		// html.println("}");
		// html.println("}]");

		html.println("});");
		if (!this.isReadonly()) {
			// html.println("grid.on('afteredit',afterEdit);");
			html.println("grid.on('edit',onGridEdit);");
		}

		html.println("});");

		if (!this.isReadonly()) {
			html.println("function updateData() {");
			html.println("var modified = store.getModifiedRecords();");
			html.println("var json = [];");
			html.println("Ext.each(modified,function(item) {json.push(item.data);});");
			html.println("if(json.length > 0) {");
			html.println("	Ext.Ajax.request({");
			html.println("	url:\"%s\",", postUrl);
			html.println("	params:{data: Ext.encode(json)},");
			html.println("	method:\"POST\",");
			if (this.onPostSuccess == null) {
				html.println("	success: function(response) {");
				// html.println("Ext.Msg.alert(\"信息\",\"数据更新成功！\",function()
				// {");
				// html.println("store.reload();");
				// html.println("});");
				html.println("		Ext.Msg.alert(\"信息\",\"数据更新成功！\");");
				html.println("	},");
			} else {
				html.println("	success:%s,", this.onPostSuccess);
			}
			html.println("	failure: function(response) {");
			html.print("		Ext.Msg.alert(\"警告\",\"数据更新失败，请稍后再试！\");");
			html.println("	}");
			html.println("	});");
			html.println("}else {");
			html.println("	Ext.Msg.alert(\"提示\",\"没有任何需要更新的数据！\");}");
			html.println("}");
		}
		html.println("</script>");
		return;
	}

	private boolean isMoreContext() {
		boolean result = false;
		for (AbstractField field : this.getFields()) {
			if (field instanceof ExpendField) {
				result = true;
				break;
			}
		}
		return result;
	}

	public String getOnPostSuccess() {
		return onPostSuccess;
	}

	public void setOnPostSuccess(String onPostSuccess) {
		this.onPostSuccess = onPostSuccess;
	}

	public boolean isExtGrid() {
		return true;
	}
}
