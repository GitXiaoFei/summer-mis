package cn.cerc.jpage.common;

import java.util.ArrayList;
import java.util.List;

public class Component {
	private List<Component> components = new ArrayList<>();
	private Component owner;

	private String id;

	public Component() {

	}

	public Component(Component owner) {
		setOwner(owner);
	}

	public void setOwner(Component owner) {
		this.owner = owner;
		if (owner != null)
			owner.addComponent(this);
	}

	public void addComponent(Component component) {
		if (!components.contains(component)) {
			components.add(component);
			if (component.getId() == null)
				component.setId("component" + components.size());
		}
	}

	public Component getOwner() {
		return owner;
	}

	public List<Component> getComponents() {
		return components;
	}

	public void output(HtmlWriter html) {
		for (Component component : components) {
			component.output(html);
		}
	}

	public String getHtml() {
		HtmlWriter html = new HtmlWriter();
		output(html);
		return html.toString();
	}

	@Override
	public final String toString() {
		HtmlWriter html = new HtmlWriter();
		output(html);
		return html.toString();
	}

	public String getId() {
		return id;
	}

	public Component setId(String id) {
		this.id = id;
		if (owner != null && id != null)
			owner.addComponent(this);
		return this;
	}

	public <T> T create(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		T obj = clazz.newInstance();
		if (!(obj instanceof Component))
			throw new RuntimeException("仅支持Component及其子数，不支持创建类型: " + clazz.getName());
		Component item = (Component) obj;
		item.setOwner(this);
		return obj;
	}

}
