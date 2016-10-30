package cn.cerc.jpage.common;

import cn.cerc.jdb.core.TDate;
import cn.cerc.jdb.core.TDateTime;

public interface IField {
	public String getString();

	default public boolean getBoolean() {
		String val = this.getString();
		return "1".equals(val) || "true".equals(val);
	}

	default public boolean getBoolean(boolean def) {
		String val = this.getString();
		if (val == null)
			return def;
		return "1".equals(val) || "true".equals(val);
	}

	default public int getInt() {
		String val = this.getString();
		if (val == null || "".equals(val))
			return 0;
		return Integer.parseInt(val);
	}

	default public int getInt(int def) {
		String val = this.getString();
		if (val == null || "".equals(val))
			return def;
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			return def;
		}
	}

	default public double getDouble() {
		String val = this.getString();
		if (val == null || "".equals(val))
			return 0;
		return Double.parseDouble(val);
	}

	default public double getDouble(double def) {
		String val = this.getString();
		if (val == null || "".equals(val))
			return def;
		try {
			return Double.parseDouble(val);
		} catch (Exception e) {
			return def;
		}
	}

	default public TDateTime getDateTime() {
		String val = this.getString();
		if (val == null)
			return null;
		return TDateTime.fromDate(val);
	}

	default public TDate getDate() {
		String val = this.getString();
		if (val == null)
			return null;
		TDateTime obj = TDateTime.fromDate(val);
		if (obj == null)
			return null;
		return new TDate(obj.getData());
	}

	default public String getString(String def) {
		String result = this.getString();
		return result != null ? result : def;
	}

	default public TDate getDate(TDate def) {
		TDate result = this.getDate();
		return result != null ? result : def;
	}

	default public TDateTime getDateTime(TDateTime def) {
		TDateTime result = this.getDateTime();
		return result != null ? result : def;
	}
}
