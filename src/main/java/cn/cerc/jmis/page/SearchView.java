package cn.cerc.jmis.page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jbean.other.MemoryBuffer;
import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.common.DataView;
import cn.cerc.jpage.common.Expender;
import cn.cerc.jpage.common.SearchItem;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.fields.ButtonField;
import cn.cerc.jpage.fields.ExpendField;
import cn.cerc.jpage.fields.Field;
import cn.cerc.jpage.form.FieldSets;

public class SearchView extends Component implements DataView {
	private Record record;
	protected String CSSClass = "search";
	protected String method = "post";
	protected HttpServletRequest request;
	protected List<Field> fields = new ArrayList<>();
	protected String action;

	private FieldSets buttons;
	private MemoryBuffer buff;
	private Component levelSide;
	private ButtonField submit;
	private boolean readAll;

	public SearchView() {
		this(null, null);
	}

	public SearchView(Component owner, HttpServletRequest request) {
		super(owner);
		this.request = request;
		this.setId("form1");
		this.setCSSClass("search");
		this.record = new Record();
	}

	public String getCSSClass() {
		return CSSClass;
	}

	public void setCSSClass(String cSSClass) {
		CSSClass = cSSClass;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	@Override
	public void addField(Field field) {
		if (field instanceof SearchItem)
			((SearchItem) field).setSearch(true);
		fields.add(field);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public void output(HtmlWriter html) {
		readAll();

		html.print("<form method=\"%s\" id=\"%s\"", this.method, this.getId());
		if (this.action != null)
			html.print(" action=\"%s\"", this.action);
		if (this.CSSClass != null)
			html.print(" class=\"%s\"", this.CSSClass);
		html.println(">");

		for (Field field : fields) {
			if (field.isHidden()) {
				field.output(html);
			}
		}

		html.println("<ul>");

		for (Field field : fields) {
			if (!field.isHidden()) {
				html.print("<li");
				if (field.getExpender() != null) {
					Expender expender = field.getExpender();
					html.print(" role='%s' style=\"display: none;\"", expender.getHiddenId());
				} else if (field.getRole() != null) {
					html.print(" role='%s'", field.getRole());
				}
				if (field instanceof ExpendField)
					html.print(" class=\"select\"");
				html.println(">");
				try {
					field.output(html);
				} catch (Exception e) {
					html.print("<label>");
					html.print(e.getMessage());
					html.print("</label>");
				}
				html.println("</li>");
			}
		}
		html.println("</ul>");
		buttons.output(html);
		html.println("<div></div>");
		html.println("</form>");

		if (levelSide != null)
			levelSide.output(html);
	}

	@Override
	public String getHtml() {
		HtmlWriter html = new HtmlWriter();
		output(html);
		return html.toString();
	}

	@Override
	public HttpServletRequest getRequest() {
		return request;
	}

	public MemoryBuffer getBuffer() {
		return buff;
	}

	public void setBuffer(MemoryBuffer buff) {
		this.buff = buff;
	}

	public FieldSets getButtons() {
		if (buttons == null)
			buttons = new FieldSets(this);
		return buttons;
	}

	public void setLevelSide(Component levelSide) {
		this.levelSide = levelSide;
	}

	public ButtonField readAll() {
		if (readAll)
			return submit;

		submit = null;
		// 取 form submit 按钮
		for (Field field : buttons.getFields()) {
			if (field instanceof ButtonField) {
				ButtonField button = (ButtonField) field;
				String key = button.getField();
				String val = request.getParameter(key);
				if (val != null && val.equals(button.getData())) {
					submit = button;
					break;
				}
			}
		}

		// 将用户值或缓存值存入到dataSet中
		for (Field field : this.fields) {
			field.updateField();
		}

		readAll = true;
		return submit;
	}

	@Override
	public void updateValue(String id, String code) {
		String val = request.getParameter(id);
		if (submit != null) {
			record.setField(code, val);
			if (buff != null)
				buff.setField(code, val);
		} else {
			if (val != null)
				record.setField(code, val);
			else if (buff != null && !buff.isNull() && buff.getRecord().exists(code))
				record.setField(code, buff.getString(code));
		}
	}

	public ButtonField getSubmit() {
		return this.submit;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
