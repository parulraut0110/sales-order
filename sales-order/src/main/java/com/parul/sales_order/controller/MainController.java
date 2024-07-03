package com.parul.sales_order.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.parul.sales_order.datasoure.config.ClassConfigTest;
import com.parul.sales_order.repository.OrderRepo;
import com.parul.sales_order.service.OrderService;
import com.parul.sales_order.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.parul.sales_order.service.OrderService;

@RestController
public class MainController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderRepo repo;
	
	TestService testService;
	
	
	@Transactional
	@GetMapping("/getItemName/{orderId}")
	public void printOrderName(@PathVariable Integer orderId) {
	   System.out.println("The order name is : " + orderService.getItemName(orderId));
	   System.out.println("The order id is : " + orderId);
	   
	   System.out.println("The orderDerails is : " + repo.getOrderDetails(1).getUnitPrice());
	   
	   
	   testService.configTest();
	 
	}
}
