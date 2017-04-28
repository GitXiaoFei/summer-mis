package cn.cerc.jpage.other;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.cerc.jdb.core.DataSet;
/**
 * 
 * 电话数据设置
 * @author hp
 * @time 2017年4月28日下午6:26:28
 *
 */
public class ExtDataSet extends DataSet {
	private static final long serialVersionUID = 1L;

	public ExtDataSet(String extJSON) {
		super();
		Gson gson = new Gson();
		List<Object> datas = gson.fromJson(extJSON, new TypeToken<List<Object>>() {
		}.getType());
		for (int i = 0; i < datas.size(); i++) {
			append();
			getCurrent().setJSON(gson.toJson(datas.get(i)));
		}
	}
}
