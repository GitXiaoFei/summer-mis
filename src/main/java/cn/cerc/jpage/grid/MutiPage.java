package cn.cerc.jpage.grid;

import net.sf.json.JSONObject;

public class MutiPage {
	// 总记录数
	private int recordCount;
	// 页面大小
	private int pageSize = 100;
	// 当前页
	private int current = 1;
	// 上一页
	private int prior;
	// 上一页
	private int next;
	// 开始记录
	private int begin;
	// 结束记录
	private int end;
	// 总页数
	private int count;

	public MutiPage() {

	}

	public MutiPage(int recordCount) {
		this.setRecordCount(recordCount);
	}

	public MutiPage(int recordCount, int current) {
		this.setRecordCount(recordCount);
		this.setCurrent(current);
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
		reset();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		reset();
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		if (current > 0 && current != this.current) {
			this.current = current;
			reset();
		}

	}

	public int getPrior() {
		return prior;
	}

	public int getNext() {
		return next;
	}

	public int getCount() {
		this.count = recordCount / pageSize;
		if ((recordCount % pageSize) > 0)
			this.count = this.count + 1;
		return this.count;
	}

	public final int getBegin() {

		return begin;
	}

	public final int getEnd() {

		return end;
	}

	private void reset() {
		// set prior:
		if (current > 1)
			this.prior = current - 1;
		else
			this.prior = 1;
		// set next:
		if (current >= this.getCount())
			this.next = current;
		else
			this.next = current + 1;
		// set begin:
		begin = (current - 1) * pageSize;
		if (begin < 0) {
			begin = 0;
		}
		if (begin >= recordCount) {
			begin = this.recordCount - 1;
		}
		// set end:
		end = (current) * pageSize - 1;
		if (end < 0) {
			end = 0;
		}
		if (end >= recordCount) {
			end = this.recordCount - 1;
		}
	}

	public String toString() {
		JSONObject json = JSONObject.fromObject(this);
		return json.toString();
	}

	public boolean isRange(int value) {
		return (value >= this.getBegin()) && (value <= this.getEnd());
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setPrior(int prior) {
		this.prior = prior;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
