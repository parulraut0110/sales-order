package com.parul.sales_order.controller;

import org.springframework.web.bind.annotation.RestController;



import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Calendar;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.transaction.annotation.Transactional;

import com.parul.sales_order.datasoure.config.ClassConfigTest;
import com.parul.sales_order.entity.Orders;
import com.parul.sales_order.repository.OrderRepo;
import com.parul.sales_order.service.OrderService;
import com.parul.sales_order.service.TestService;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.parul.sales_order.service.OrderService;

import com.parul.sales_order.dto.OrderDTO;


@RestController
public class MainController {

	@Autowired
	OrderService orderService;

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
		Date startDate = sdf.parse("2024-08-13");               //create a 'Data' object corresponding to string input provided
		Date endDate = sdf.parse("2024-08-17");

		List<Orders> orderListByOrderDate = repo.findByOrderDateBetween(startDate, endDate);
		System.out.println("orderListByOrderDate ");
		for(Orders o : orderListByOrderDate) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate());	

		System.out.println();


		List<Orders> orderListByOrderDateGreaterThan = repo.findByOrderDateGreaterThan(startDate);
		System.out.println("orderListByOrderDateGreaterThan ");
		for(Orders o : orderListByOrderDateGreaterThan) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate());	

		System.out.println();


		List<Orders> orderListByOrderDetailsStartingWith = repo.findByOrderDetailsStartingWith("Smart");
		System.out.println("orderListByOrderDetailsStartingWith  : Smart ");
		for(Orders o : orderListByOrderDetailsStartingWith) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate());	

		System.out.println();

		List<Orders> orderListByOrderDetailsEndingWith = repo.findByOrderDetailsEndingWith("er");
		System.out.println("orderListByOrderDetailsEndingWith  : er ");
		for(Orders o : orderListByOrderDetailsEndingWith) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate());	

		System.out.println();
		/*
		List<Orders> orderListByOrdersInPriceRange = repo.getOrdersInPriceRange(100.00F, 500.00F);
		for(Orders o : orderListByOrdersInPriceRange) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate());	
		 */
		System.out.println();

		/*
		List<OrderDTO> orderListByPriceRange = repo.ordersInPriceLimit(150.00F, 1000.00F);
		for(OrderDTO o : orderListByPriceRange) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate());	

		System.out.println();
		 */


		List<OrderDTO> orderListByPriceRange = repo.ordersInPriceLimit(150.00F, 1000.00F);
		System.out.println("orderListByPriceRange");
		for(OrderDTO o : orderListByPriceRange) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate().toGMTString());	

		System.out.println();


		/*
		ScrollPosition position = ScrollPosition.offset();
		System.out.println("position : " + position.toString());
		Window<Orders> orders = repo.findFirst10ByUnitPriceGreaterThan(0.00F, position);


		do {
			for (Orders o : orders) 
				System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate().toGMTString());

			if (!orders.isEmpty() && orders.hasNext()) {
				// Ensure that positionAt returns an OffsetScrollPosition
				position = orders.positionAt(orders.size() - 1);
				orders = repo.findFirst10ByUnitPriceGreaterThan(0.00F, position);
			} else {
				break;
			}
		} while (true);
		 */

		Window<Orders> orders = repo.findFirst10ByUnitPriceGreaterThan(0.0F, ScrollPosition.offset());          //offset returns the scroll position value as an object at beginning it returns 0 but there after advances the scroll position corresponding the windows size    

		do {
			for (Orders o : orders) 
				System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate());        

			orders = repo.findFirst10ByUnitPriceGreaterThan(0.0F, orders.positionAt(orders.size() - 1));
		} while (!orders.isEmpty());
		System.out.println();


		int pageNo = 0;
		int pageSize = 10;
		Page<Orders> ordersByPage = repo.findFirst6ByUnitPriceGreaterThan(50.0F, PageRequest.of(pageNo, pageSize, Sort.by("unitPrice").ascending().and(Sort.by("orderId").ascending().and(Sort.by("quantity").descending()))));          

		do {			
			ordersByPage.forEach(order -> System.out.println(
					order.getOrderId() + " " + order.getOrderDetails() + " " + order.getUnitPrice() + " " + order.getQuantity() + " " + order.getOrderDate()));

			ordersByPage = repo.findFirst6ByUnitPriceGreaterThan(50.0F, PageRequest.of(++pageNo, pageSize, Sort.by("unitPrice").ascending().and(Sort.by("orderId").ascending().and(Sort.by("quantity").descending()))));          

		} while (ordersByPage.hasNext());
		System.out.println();


		System.out.println("\nunsorted Orders\n");
		List<Orders> allOrders = repo.findAllOrders();
		for(Orders o : allOrders) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate().toGMTString());	

		System.out.println();


		System.out.println("\nSorted Orders\n");
		//List<Orders> getAllOrders = repo.getAllOrders(Sort.sort(Orders.class).by("orderId").ascending());

		List<Orders> getAllOrders = repo.getAllOrders(
				Sort.by(Sort.Order.asc("orderId"))
				.and(Sort.by(Sort.Order.asc("unitPrice")))
				.and(Sort.by(Sort.Order.asc("orderDetails")))
				);

		for (Orders o : getAllOrders) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate().toGMTString());

		System.out.println();



		pageNo = 0;
		Page<Orders> getAllOrdersByPage;
		do {
			getAllOrdersByPage = repo.getAllOrdersByPage(PageRequest.of(pageNo, 10));

			for (Orders o : getAllOrdersByPage.getContent())                          //getContents provides the page content as a list 
				System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate().toGMTString());
			pageNo++;
			getAllOrdersByPage = repo.getAllOrdersByPage(PageRequest.of(pageNo, 10));

		} while(getAllOrdersByPage.hasNext());  
		System.out.println();



		System.out.println("List orders by example");
		List<Orders> ordersByExample = orderService.ordersExample("Smart Watch");
		for(Orders o : ordersByExample) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate().toGMTString());	

		System.out.println();
		System.out.println("List orders starts with by example");
		List<Orders> ordersExample = orderService.ordersByExample("Smart");
		for(Orders o : ordersExample) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate().toGMTString());	
		System.out.println();
		/*
		@GetMapping("/orders/example-price-range")
	    public List<Orders> getOrdersByExampleAndPriceRange(@RequestParam String orderDetails,
	                                                        @RequestParam double minPrice,
	                                                        @RequestParam double maxPrice) {
	        return orderService.findOrdersByExampleAndPriceRange(orderDetails, minPrice, maxPrice);
	    }
		 */
		System.out.println("order list in price range");
		List<Orders> ordersByExampleInPriceRange = orderService.findOrdersByExampleAndPriceRange("Smart", 110.00F, 900.00F);
		for(Orders o : ordersByExampleInPriceRange) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate().toGMTString());	

		System.out.println("\n\norder list for specific quantity");
		List<Orders> ordersByQuantityExample = orderService.ordersByQuantityExample(2);
		for(Orders o : ordersByQuantityExample) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate().toGMTString());	


		System.out.println("\n\nOrder list for specific order date:");
		List<Orders> ordersByDateExample = orderService.ordersByDetailsExample("Smart *");
		for (Orders o : ordersByDateExample) {
		    System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate().toGMTString());
		}
	}

}


