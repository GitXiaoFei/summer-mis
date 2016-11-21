package cn.cerc.jpage.core;

public class HtmlWriter {
	private StringBuffer sl = new StringBuffer();

	public void print(String value) {
		sl.append(value);
	}

	public void print(String format, Object... args) {
		sl.append(String.format(format, args));
	}

	public void println(String value) {
		sl.append(value);
		sl.append(cn.cerc.jdb.other.utils.vbCrLf);
	}

	public void println(String format, Object... args) {
		sl.append(String.format(format, args));
		sl.append(cn.cerc.jdb.other.utils.vbCrLf);
	}

	@Override
	public String toString() {
		return sl.toString();
	}
}
