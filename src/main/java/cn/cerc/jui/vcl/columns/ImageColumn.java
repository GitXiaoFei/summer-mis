package cn.cerc.jui.vcl.columns;

import cn.cerc.jdb.core.Record;
import cn.cerc.jpage.core.Component;

public class ImageColumn extends AbstractColumn {

	private Component image;

	public ImageColumn(Component owner) {
		super(owner);
	}

	@Override
	public String format(Object value) {
		Image img = null;
		if (value instanceof Record) {
			Record rs = (Record) value;
			img = new Image(this.getTitle(), rs.getString(this.getField()));
			return String.format("<img src='%s' alt='%s' width='%s' height='%s' />", img.getUrl(), img.getName(),
					img.getWidth(), img.getHeight());
		}else{
			img = (Image) value;
			return String.format("<img src='%s' alt='%s' width='%s' height='%s' />", img.getUrl(), img.getName(),
					img.getWidth(), img.getHeight());
		}

	}

	public void add(String name, String imgUrl) {
		this.getGrid().getCurrent().add(this, new Image(name, imgUrl));
	}

	public Component getImage() {
		return image;
	}

	public void setImage(Component image) {
		this.image = image;
	}

}
