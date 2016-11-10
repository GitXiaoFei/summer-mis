package cn.cerc.jpage.other;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.cerc.jdb.core.DataSet;
import cn.cerc.jpage.grid.MutiPage;

public class DBGrid<T> {
	private DataSet dataset;
	private MutiPage pages = new MutiPage();
	private List<T> items = new ArrayList<T>();

	public DBGrid(DataSet dataset) {
		this.setDataset(dataset);
	}

	public DataSet getDataset() {
		return dataset;
	}

	public void setDataset(DataSet dataset) {
		this.dataset = dataset;
	}

	public int map(HttpServletRequest req, Class<T> clazz) {
		return this.map(req, clazz, null, true);
	}

	public int map(HttpServletRequest req, Class<T> clazz, BuildRecord make, boolean defProcess) {
		BeanRecord<T> defMake = null;
		try {
			if (defProcess)
				defMake = new BeanRecord<T>();
			T item = null;
			int pageno = 1;
			String tmp = req.getParameter("pageno");
			if (tmp != null && !tmp.equals("")) {
				pageno = Integer.parseInt(tmp);
			}
			pages.setRecordCount(dataset.size());
			pages.setCurrent(pageno);
			if (dataset.size() == 0)
				return 0;

			int i = pages.getBegin();
			while (i <= pages.getEnd()) {
				dataset.setRecNo(i + 1);
				item = clazz.newInstance();
				if (defProcess)
					defMake.build(item, dataset.getCurrent());
				if (make != null)
					make.build(item, dataset.getCurrent());

				items.add(item);
				i++;
			}
			return items.size();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (defMake != null)
				defMake.close();
		}
	}

	public List<T> getList() {
		return items;
	}

	public MutiPage getPages() {
		return pages;
	}

}
