package com.parul.sales_order.controller;

import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
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
import org.springframework.format.datetime.DateFormatter;
import org.springframework.transaction.annotation.Transactional;

import com.parul.sales_order.datasoure.config.ClassConfigTest;
import com.parul.sales_order.entity.Orders;
import com.parul.sales_order.repository.JDBCTemplateOrderRepo;
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

	@Autowired
	JDBCTemplateOrderRepo jdbcTemplateRepo;

	@Transactional
	@GetMapping("/getItemName/{orderId}")
	public void printOrderName(@PathVariable Integer orderId) throws ParseException, SQLException {

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
		Date startDate = sdf.parse("2024-08-13");               //create a 'Date' object corresponding to string input provided
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

		/*
		System.out.println("\n\nOrder list for specific order date:");
		List<Orders> ordersByDateExample = orderService.ordersByDetailsExample("Smart *");
		for (Orders o : ordersByDateExample) {
		    System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate().toGMTString());
		}
		 */

		//jdbcTemplateRepo.performInserts();

		List<Orders> insertArgs = new ArrayList<>();
		Orders order = new Orders();
		order.setOrderDetails("Office Bag");
		order.setQuantity(2);
		order.setUnitPrice(1200.00F);
		order.setOrderDate(new Date());
		insertArgs.add(order);

		order = new Orders();
		order.setOrderDetails("shopping Bag");
		order.setQuantity(3);
		order.setUnitPrice(800.00F);
		order.setOrderDate(new Date());
		insertArgs.add(order);

		order = new Orders();
		order.setOrderDetails("Sports Bag");
		order.setQuantity(2);
		order.setUnitPrice(1900.00F);
		order.setOrderDate(new Date());
		insertArgs.add(order);

		int[][] updateStats = jdbcTemplateRepo.performInsertsByArgs(insertArgs);
		for(int i = 0; i < updateStats.length; i++) 
			for(int j = 0; j < updateStats[i].length; j++) 
				System.out.printf("\n[%d][%d] : %d", i, j, updateStats[i][j]);



		//jdbcTemplateRepo.performBatchInsertByArgs(insertArgs);

		//jdbcTemplateRepo.performBatchInsertByBatchSetter(insertArgs);

		List<Orders> insertArgs1 = new ArrayList<>();
		order = new Orders();
		order.setOrderDetails("Head phone");
		order.setQuantity(1);
		order.setUnitPrice(200.00F);
		order.setOrderDate(new Date());
		insertArgs1.add(order);

		order = new Orders();
		order.setOrderDetails("Ear Phone");
		order.setQuantity(2);
		order.setUnitPrice(100.00F);
		order.setOrderDate(new Date());
		insertArgs1.add(order);

		order = new Orders();
		order.setOrderDetails("Cordless Phone");
		order.setQuantity(1);
		order.setUnitPrice(10900.00F);
		order.setOrderDate(new Date());
		insertArgs1.add(order);

		//jdbcTemplateRepo.performBatchInsertByArgAndTypes(insertArgs1);


		//jdbcTemplateRepo.performBatchInsertWithKey(insertArgs1);


		List<Orders> orderList = jdbcTemplateRepo.getOrdersAboveByCallable(500.00F);
		System.out.println("\n\norder list for Orders Above ");
		for(Orders o : orderList) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate().toGMTString());	

		List<Orders> getOrdersAboveByCallable = jdbcTemplateRepo.getOrdersAboveByCallable(150.00F);
		System.out.println("\n\norder list for Orders Above ");
		for(Orders o : getOrdersAboveByCallable) 
			System.out.println(o.getOrderId() + " " + o.getOrderDetails() + " " + o.getQuantity() + " " + o.getOrderDate().toGMTString());	

		List<Map<String, Object>> getOrdersAboveByCallableUsingSqlResultSet = jdbcTemplateRepo.getOrdersAboveByCallableUsingSqlResultSet(750.00F);
		System.out.println("\n\norder list for Orders Above:");

		for (Map<String, Object> order1 : getOrdersAboveByCallableUsingSqlResultSet) {
			System.out.println(
					"Order ID: " + order1.get("orderid") + ", " +
							"Order Details: " + order1.get("orderdetails") + ", " +
							"Quantity: " + order1.get("quantity") + ", " +
							"Price: " + order1.get("price") + ", " +
							"Order Date: " + order1.get("orderdate")
					);
		}

		Map<String, Object> maxOrder = jdbcTemplateRepo.getMaxOrderPriceByCallableUsingResultSetExtractor(0.00F);
		System.out.println("\n\norder Details of Max Order:");

		System.out.println(
				"Order ID: " + maxOrder.get("orderId") + ", " +
						"Order Details: " + maxOrder.get("orderDetails") + ", " +
						"Quantity: " + maxOrder.get("quantity") + ", " +
						"Price: " + maxOrder.get("unitPrice") + ", " +
						"Order Date: " + maxOrder.get("orderDate"));

		System.out.println("\nQuery Update count");
		int updateCount = jdbcTemplateRepo.updateOrdersByPriceAndGetUpdateCount(7700.00F, 10.00F);
		System.out.println("Update Count : " + updateCount);

		/*
		System.out.println("\nexecuteSqlStatement : ");
		jdbcTemplateRepo.executeSqlStatement();
		 */		

		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate1 = dateFormatter.parse("2024-07-31");
		Date endDate1 = dateFormatter.parse("2024-08-08");

		System.out.println("\nOrders between \n");
		List<Map<String, Object>> ordersBetween = jdbcTemplateRepo.getOrdersByPriceAndInDateBetween(2000.00F, startDate1, endDate1);
		ordersBetween.forEach(m -> System.out.println("OrderDetails : " + (String)m.get("OrderDetails") + " Price : " + (float)m.get("Price")));

		System.out.println("\nOrders Stats \n");
		Map<String, Object> orderStats = jdbcTemplateRepo.getOrderStatsBetweenDates(startDate1, endDate1);
		System.out.println("average order Price : " + orderStats.get("avrg") + " minimum count price : " + orderStats.get("minPrice") + " count of orders : " + orderStats.get("orderCount"));


		Date startDate2 = dateFormatter.parse("2024-07-04");
		Date endDate2 = dateFormatter.parse("2024-07-06");


		System.out.println("\n\norder Stats by procedure:");
		Map<String, Object> orderStats2 = jdbcTemplateRepo.getOrderStatsBetweenDatesUsingCallable(startDate2, endDate2);
		System.out.println("average order Price : " + orderStats2.get("avrg") + " minimum count price : " + orderStats2.get("minPrice") + " count of orders : " + orderStats2.get("orderCount"));

		Date date = dateFormatter.parse("2024-08-01");

		System.out.println("\n\ntotal orders on Date\n");
		Map<Date, Float> totalOrder = jdbcTemplateRepo.totalOrdersOnDate(date);
		System.out.println("Total Order on " + date.toString() + " : " + totalOrder.get(date));

		Date startDate3 = dateFormatter.parse("2024-07-01");
		Date endDate3 = dateFormatter.parse("2024-07-02");

		System.out.println("Max Order Between Dates ");
		Map<Date, Float> MaxOrderBetweenDate = jdbcTemplateRepo.getMaxOrderBetweenDate(startDate3, endDate3);
		for(Map.Entry<Date, Float>  entry : MaxOrderBetweenDate.entrySet())
			System.out.println(entry.getKey() + " " + entry.getValue());

		System.out.println("\nMax Order on Each Day\n");
		List<Map<String, Object>> maxOrderForEachDate = jdbcTemplateRepo.selectMaxOrderForEachDate();
		for (Map<String, Object> order1 : maxOrderForEachDate) {
			System.out.println(
							"Order ID: " + order1.get("orderId") + ", " +
							"Order Details: " + order1.get("orderDetails") + ", " +
							"Order amount: " + order1.get("max") + ", " +
							"Order Date: " + order1.get("orderDate")
					);
		}

		System.out.println("\nOrders Above average\n");
		List<Map<String, Object>> ordersAboveAverage = jdbcTemplateRepo.getAboveAverageOrders();
		for (Map<String, Object> order1 : ordersAboveAverage) {
			System.out.println(
							"Order ID: " + order1.get("orderId") + ", " +
							"Order Price: " + order1.get("price") + ", " +
							"Order Quantity: " + order1.get("quantity") + ", " +
							"Order Date: " + order1.get("orderDetails") + ", " + 
							"Order Date: " + order1.get("orderDate")
					);
		}
		
		java.util.Date date1 = dateFormatter.parse("2024-08-02");
		
		System.out.println("\nOrder Volume on Date\n");
		Map<String, Object> productSalesVolumeOnDate  = jdbcTemplateRepo.getProductSalesVolumeOnDate(date1, "Mobile Phone");
		System.out.println("Order_Date : " + ((java.sql.Date)productSalesVolumeOnDate.get("orderDate")).toString()+ " Order_Details : " + (String)productSalesVolumeOnDate.get("orderDetails") + " Volume : " + (Integer)productSalesVolumeOnDate.get("orderVolume"));

	}

}



