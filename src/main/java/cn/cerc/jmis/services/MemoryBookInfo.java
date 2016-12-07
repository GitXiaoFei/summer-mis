package cn.cerc.jmis.services;

import com.google.gson.Gson;

import cn.cerc.jbean.client.LocalService;
import cn.cerc.jbean.core.Application;
import cn.cerc.jbean.other.BookVersion;
import cn.cerc.jbean.other.BufferType;
import cn.cerc.jdb.cache.IMemcache;
import cn.cerc.jdb.core.IHandle;
import cn.cerc.jdb.core.Record;

public class MemoryBookInfo {
	private static final String buffVersion = "3";

	public static BookInfoRecord get(IHandle handle, String corpNo) {
		IMemcache buff = Application.getMemcache();

		String tmp = (String) buff.get(getBuffKey(corpNo));
		if (tmp == null || "".equals(tmp)) {
			LocalService svr = new LocalService(handle, "SvrBookInfo.getRecord");
			if (!svr.exec("corpNo", corpNo))
				return null;
			
			BookInfoRecord result = new BookInfoRecord();
			Record ds = svr.getDataOut().getHead();
			result.setCode(ds.getString("corpNo"));
			result.setShortName(ds.getString("shortName"));
			result.setName(ds.getString("name"));
			result.setAddress(ds.getString("address"));
			result.setTel(ds.getString("tel"));
			result.setManagerPhone(ds.getString("managerPhone"));
			result.setStartHost(ds.getString("host"));
			result.setContact(ds.getString("contact"));
			result.setAuthentication(ds.getBoolean("authentication"));

			result.setStatus(ds.getInt("status"));
			result.setCorpType(ds.getInt("type"));
			
			Gson gson = new Gson();
			buff.set(getBuffKey(corpNo), gson.toJson(result));

			return result;
		} else {
			Gson gson = new Gson();
			return gson.fromJson(tmp, BookInfoRecord.class);
		}
	}

	/*
	 * 返回帐套状态
	 */
	public static int getStatus(IHandle handle, String corpNo) {
		BookInfoRecord item = get(handle, corpNo);
		if (item == null)
			throw new RuntimeException(String.format("没有找到注册的帐套  %s ", corpNo));
		return item.getStatus();
	}

	/*
	 * 返回当前帐套的版本：基础版还是其它版本
	 */
	public static BookVersion getBookType(IHandle handle) {
		String corpNo = handle.getCorpNo();
		return getCorpType(handle, corpNo);
	}

	/*
	 * 返回指定帐套的版本：基础版还是其它版本
	 */
	public static BookVersion getCorpType(IHandle handle, String corpNo) {
		BookInfoRecord item = get(handle, corpNo);
		if (item == null)
			throw new RuntimeException(String.format("没有找到注册的帐套  %s ", corpNo));
		int result = item.getCorpType();
		return BookVersion.values()[result];
	}

	/*
	 * 返回帐套简称
	 */
	public static String getShortName(IHandle handle, String corpNo) {
		BookInfoRecord item = get(handle, corpNo);
		if (item == null)
			throw new RuntimeException(String.format("没有找到注册的帐套  %s ", corpNo));
		return item.getShortName();
	}

	public static void clear(String corpNo) {
		IMemcache buff = Application.getMemcache();
		buff.delete(getBuffKey(corpNo));
	}

	private static String getBuffKey(String corpNo) {
		return String.format("%s.%s.%s", BufferType.getOurInfo, corpNo, buffVersion);
	}
}
