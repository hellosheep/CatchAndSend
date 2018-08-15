package com.offcn;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppStart {
	
	public static void main(String[] args) throws Exception {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		context.start();
		System.in.read();
		context.close();
		
	}
}
