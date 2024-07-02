package com.parul.sales_order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.parul.sales_order.datasoure.config.ClassConfigTest;

@Component
public class TestService {
	@Autowired
	ClassConfigTest configTest;
	
	@Bean
	public void configTest() {
		System.out.println("Testing DependsOn Annotation");
	}
}
