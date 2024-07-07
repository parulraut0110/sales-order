package com.parul.sales_order.controller;

import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.parul.sales_order.datasoure.config.ClassConfigTest;
import com.parul.sales_order.entity.Orders;
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

	//@Autowired
	//OrderService orderService;



	@Autowired
	TestService testService;

	@Autowired
	OrderRepo repo;

	@Transactional
	@GetMapping("/getItemName/{orderId}")
	public void printOrderName(@PathVariable Integer orderId) throws ParseException {


		//System.out.println("The order name is : " + orderService.getItemName(orderId));
		System.out.println("The order id is : " + orderId);

		System.out.println("The orderDerails is : " + repo.getOrderDetails(1).getUnitPrice());

		/*
	   List<Orders> orderList = repo.getOrders(100);
	   for(Orders o : orderList) 
		   System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity());
		 */
		
		List<Orders> orderListByDetails = repo.getDetails("ve soap");
		for(Orders o : orderListByDetails) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity());	
		
		System.out.println();

		List<Orders> orderListByPriceAndQuantity = repo.findByUnitPriceAndQuantity(3.50F, 10);
		for(Orders o : orderListByPriceAndQuantity) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity());	
		
		System.out.println();

		List<Orders> orderListByOrderDetails = repo.findDistinctByorderDetails("Dove");
		for(Orders o : orderListByOrderDetails) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity());	
		
		System.out.println();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse("2024-08-13");
		Date endDate = sdf.parse("2024-08-17");
		
		List<Orders> orderListByOrderDate = repo.findByOrderDateBetween(startDate, endDate);
		for(Orders o : orderListByOrderDate) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate());	

		System.out.println();
		
		
		List<Orders> orderListByOrderDateGreaterThan = repo.findByOrderDateGreaterThan(startDate);
		for(Orders o : orderListByOrderDateGreaterThan) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate());	

		System.out.println();
		
		
		List<Orders> orderListByOrderDetailsStartingWith = repo.findByOrderDetailsStartingWith("Smart");
		for(Orders o : orderListByOrderDetailsStartingWith) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate());	

		System.out.println();
		
		List<Orders> orderListByOrderDetailsEndingWith = repo.findByOrderDetailsEndingWith("er");
		for(Orders o : orderListByOrderDetailsEndingWith) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate());	

		System.out.println();
		/*
		List<Orders> orderListByOrdersInPriceRange = repo.getOrdersInPriceRange(100.00F, 500.00F);
		for(Orders o : orderListByOrdersInPriceRange) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate());	
		 */
		System.out.println();
		
		List<Orders> orderListByPriceRange = repo.ordersForPriceLimits(150.00F, 1000.00F);
		for(Orders o : orderListByPriceRange) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate());	

		System.out.println();
		
	}
}
