package com.offcn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offcn.service.FruitService;

@Controller
public class FruitController {
	
	@Autowired
	FruitService fruitService;
	
	@RequestMapping("getAvg")
	@ResponseBody
	public List<Object> getAvg(int month){
		System.out.println(month);
		//获取请求所需月份的数据
		List<Double> avglist2017 = fruitService.getAvgAsYM(2017, month);
		List<Double> avglist2018 = fruitService.getAvgAsYM(2018, month);
		
		List<Object> list = new ArrayList<Object>();
		list.add(avglist2017);
		list.add(avglist2018);
		System.out.println(list);
		return list;
	}
	
	public void test(){
		System.out.println("测试代码冲突");
	}
	
	public void demo2(){
		System.out.println("测试代码冲突 user2");
	}

}
