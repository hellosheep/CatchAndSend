package com.offcn.po;

public class Sp {
	
	private String name;
	private float price;
	private String pic;
	private String url;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "sp [name=" + name + ", price=" + price + ", pic=" + pic + ", url=" + url + "]";
	}
	
	
}
