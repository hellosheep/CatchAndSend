package com.offcn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.offcn.po.Fruit;

public interface FruitDao {
	
	public void save(Fruit fruit);
	
	public List<Double> getAvgAsYM(int year, int month);
	
	public void deleteAll();
	
	public Fruit getFruitByDate(@Param("date")String date);
	
}
