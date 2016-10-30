package cn.cerc.jmis.page;

import com.google.gson.Gson;

public class ResultMessage {
	private boolean result;
	private String message;
	private String data;

	public ResultMessage() {

	}

	public ResultMessage(boolean result, String message) {
		this.result = result;
		this.message = message;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
