package cn.cerc.jpage.other;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import cn.cerc.jdb.core.Record;

public class BeanRecord<T> implements AutoCloseable, BuildRecord {

	private Map<Field, Method> gets = new HashMap<>();
	private Map<Field, Method> sets = new HashMap<>();
	private Class<?> clazz;

	private void init(Class<?> clazz) {
		if (this.clazz != null && clazz != null && this.clazz.getName().equals(clazz.getName())) {
			return;
		}
		gets.clear();
		sets.clear();
		// log.info("init :" + clazz.getName());
		this.clazz = clazz;
		Map<String, Method> items0 = new HashMap<>();
		Map<String, Method> items1 = new HashMap<>();
		try {
			// log.info(clazz.getName());
			Field[] fields = clazz.getDeclaredFields();
			Method[] methods = clazz.getDeclaredMethods();
			for (Method f : methods) {
				if (f.getModifiers() == 1 && f.getParameterTypes().length == 0 && f.getName().startsWith("get")) {
					// String tp = f.getReturnType().getName();
					// log.info(f.getName() + ":" + tp + ":" +
					// Modifier.toString(f.getModifiers()));
					items0.put(f.getName(), f);
				}
				if (f.getModifiers() == 1 && f.getParameterTypes().length == 1 && f.getName().startsWith("set")) {
					// String tp = f.getReturnType().getName();
					// log.info(f.getName() + ":" + tp + ":" +
					// Modifier.toString(f.getModifiers()));
					items1.put(f.getName(), f);
				}
			}
			for (Field f : fields) {
				String field = f.getName();
				if (Modifier.toString(f.getModifiers()).equals("private")) {
					String func = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
					Method item = items0.get(func);
					if (item != null && item.getReturnType().equals(f.getType())) {
						gets.put(f, item);
					}

					func = "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
					item = items1.get(func);
					if (item != null) {
						sets.put(f, item);
					}
				}
			}

			// log.info(gets.size());
			// log.info(sets.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			items0.clear();
			items1.clear();
		}

	}

	public void saveTo(T owner, Record record) {
		init(owner.getClass());
		try {
			for (Field f : gets.keySet()) {
				String field = f.getName();
				Object value = gets.get(f).invoke(owner);
				record.setField(field, value);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void build(Object owner, Record record) {
		init(owner.getClass());
		try {
			for (Field f : sets.keySet()) {
				String field = f.getName();
				if (record.exists(field)) {
					Object value = record.getField(field);
					Method mt = sets.get(f);
					Class<?> p1 = mt.getParameterTypes()[0];
					if (p1.getName().equals(value.getClass().getName()))
						mt.invoke(owner, value);
					else if (p1.getName().equals("java.lang.String"))
						mt.invoke(owner, value.toString());
					else if (p1.getName().equals("int") && value.getClass().getName().equals("java.lang.Integer")) {
						int val = (Integer) value;
						mt.invoke(owner, val);
					} else if (p1.getName().equals("double") && value.getClass().getName().equals("java.lang.Double")) {
						double val = (Double) value;
						mt.invoke(owner, val);
					} else {
						String str = String.format("数据字段(%s)的数据类型(%s)与要赋值的对象属性类型(%s)不一致！", field,
								value.getClass().getName(), p1.getName());
						throw new RuntimeException(str);
					}
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void close() {
		gets.clear();
		sets.clear();
	}
}
