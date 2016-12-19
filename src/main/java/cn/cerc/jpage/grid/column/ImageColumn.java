package cn.cerc.jpage.grid.column;

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
		this.getGrid().getCurrent().addData(this, new Image(name, imgUrl));
	}

	public Component getImage() {
		return image;
	}

	public void setImage(Component image) {
		this.image = image;
	}
	public class Image {
		private String name;
		private String url;
		private int width=16;
		private int height=16;
		
		
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public Image(String name, String url) {
			super();
			this.name = name;
			this.url = url;
		}
	}

}
