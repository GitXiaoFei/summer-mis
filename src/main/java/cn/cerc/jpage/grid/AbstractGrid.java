package cn.cerc.jpage.grid;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.jbean.form.IForm;
import cn.cerc.jdb.core.DataSet;
import cn.cerc.jpage.core.ActionForm;
import cn.cerc.jpage.core.Component;
import cn.cerc.jpage.core.DataSource;
import cn.cerc.jpage.core.HtmlWriter;
import cn.cerc.jpage.core.IField;
import cn.cerc.jpage.fields.AbstractField;
import cn.cerc.jpage.grid.lines.AbstractGridLine;
import cn.cerc.jpage.grid.lines.ChildGridLine;
import cn.cerc.jpage.grid.lines.MasterGridLine;

public abstract class AbstractGrid extends Component implements DataSource {
	// 数据源
	private DataSet dataSet;
	// 支持表格分页
	private MutiPage pages = new MutiPage();
	// 行管理器, 其中第1个一定为masterLine
	private List<AbstractGridLine> lines = new ArrayList<>();
	// 主行
	protected MasterGridLine masterLine;
	// 表单，后不得再使用
	protected ActionForm form;

	public AbstractGrid(IForm form, Component owner) {
		super(owner);
		this.setId("grid");
		masterLine = new MasterGridLine(this);
		lines.add(masterLine);
		pages.setRequest(form.getRequest());
	}

	@Override
	public DataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
		pages.setDataSet(dataSet);
	}

	@Override
	public void addField(IField field) {
		if(field instanceof AbstractField){
			AbstractField obj = (AbstractField) field;
			obj.setOwner(masterLine);
		}
		masterLine.addField(field);
	}

	public MutiPage getPages() {
		return pages;
	}

	public List<AbstractField> getFields() {
		List<AbstractField> items = new ArrayList<>();
		for (IField obj : lines.get(0).getFields()) {
			if (obj instanceof AbstractField)
				items.add((AbstractField) obj);
		}
		return items;
	}

	@Deprecated
	public ActionForm getForm() {
		return form;
	}

	@Deprecated
	public void setForm(ActionForm form) {
		this.form = form;
	}

	public abstract void outputGrid(HtmlWriter html);

	public abstract Component getExpender();

	public List<AbstractGridLine> getLines() {
		return lines;
	}

	public AbstractGridLine getLine(int index) {
		if (index == lines.size())
			lines.add(new ChildGridLine(this));
		return lines.get(index);
	}

	public MasterGridLine getMasterLine() {
		return masterLine;
	}
}
