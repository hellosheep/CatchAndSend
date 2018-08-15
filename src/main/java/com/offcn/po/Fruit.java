package com.offcn.po;

import java.util.Date;

public class Fruit {
	private int id;
	private String name;
	private double minprice;
	private double avgprice;
	private double maxprice;
	private String category;
	private String unit;
	private Date dateofissue;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMinprice() {
		return minprice;
	}
	public void setMinprice(double minprice) {
		this.minprice = minprice;
	}
	public double getAvgprice() {
		return avgprice;
	}
	public void setAvgprice(double avgprice) {
		this.avgprice = avgprice;
	}
	public double getMaxprice() {
		return maxprice;
	}
	public void setMaxprice(double maxprice) {
		this.maxprice = maxprice;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Date getDateofissue() {
		return dateofissue;
	}
	public void setDateofissue(Date dateofissue) {
		this.dateofissue = dateofissue;
	}
	
	@Override
	public String toString() {
		return "Fruit [id=" + id + ", name=" + name + ", minprice=" + minprice + ", avgprice=" + avgprice
				+ ", maxprice=" + maxprice + ", category=" + category + ", unit=" + unit + ", dateofissue="
				+ dateofissue + "]";
	}
	
	
	
}
