package com.offcn.service;

import java.util.List;

import com.offcn.po.Fruit;

public interface FruitService {
	
	public void save(Fruit fruit);
	
	public List<Double> getAvgAsYM(int year, int month);
	
	public void deleteAll();
	
	public Fruit getFruitByDate(String date);
}
