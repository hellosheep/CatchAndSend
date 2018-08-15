package com.offcn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offcn.dao.FruitDao;
import com.offcn.po.Fruit;

@Service
public class FruitServiceImpl implements FruitService {
	
	@Autowired
	FruitDao fruitDao;

	@Override
	public void save(Fruit fruit) {
		fruitDao.save(fruit);	
	}

	@Override
	public List<Double> getAvgAsYM(int year, int month) {
		List<Double> avglist = fruitDao.getAvgAsYM(year, month);
		return avglist;
	}

	@Override
	public void deleteAll() {
		fruitDao.deleteAll();
		
	}

	@Override
	public Fruit getFruitByDate(String date) {
		
		return fruitDao.getFruitByDate(date);
	}
	
	
	
}
