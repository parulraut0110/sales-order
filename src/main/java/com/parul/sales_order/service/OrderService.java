package com.parul.sales_order.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.parul.sales_order.entity.Orders;
import com.parul.sales_order.repository.OrderRepo;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {


	@Autowired
	private OrderRepo orderRepo;

	/*
    @Transactional
    public String getItemName(int orderId) {
        return orderRepo.getItemName(orderId);
    }
	 */

	public List<Orders> ordersExample(String name) {
		Orders orderProbe = new Orders();          //1 create probe 
		orderProbe.setOrderDetails(name);          //2 set probe properties 
		ExampleMatcher matcher = ExampleMatcher.matching()    //3 create search criteria 
				.withIgnoreNullValues()
				.withIgnorePaths("orderId", "unitPrice", "quantity" ,"orderDate")
				.withMatcher("orderDetails", new ExampleMatcher.GenericPropertyMatcher().exact());
		Example<Orders> orderExample = Example.of(orderProbe, matcher);   //create example object 


		return orderRepo.findAll(orderExample);   //invoke a method to use the example object as search object 
	}

	public List<Orders> ordersByExample(String name) {
		// Create a probe with the search criteria
		Orders orderProbe = new Orders();
		orderProbe.setOrderDetails(name);

		// Define an ExampleMatcher to use the 'startsWith' strategy
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withIgnorePaths("orderId", "unitPrice", "quantity", "orderDate")
				.withMatcher("orderDetails", ExampleMatcher.GenericPropertyMatchers.contains());

		// Create an Example using the probe and matcher
		Example<Orders> orderExample = Example.of(orderProbe, matcher);

		// Execute the query
		return orderRepo.findAll(orderExample);
	}

	public List<Orders> findOrdersByExampleAndPriceRange(String orderDetails, float minPrice, float maxPrice) {
		// Create a probe with the search criteria
		Orders orderProbe = new Orders();
		orderProbe.setOrderDetails(orderDetails);

		// Define an ExampleMatcher for fields you want to match exactly
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withIgnorePaths("orderId", "unitPrice", "quantity", "orderDate")
				.withMatcher("orderDetails", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

		// Create an Example using the probe and matcher
		Example<Orders> orderExample = Example.of(orderProbe, matcher);

		// Find all matching examples and filter by price range in memory
		return orderRepo.findAll(orderExample).stream()
				.filter(order -> order.getUnitPrice() >= minPrice && order.getUnitPrice() <= maxPrice)
				.collect(Collectors.toList());
	}


	public List<Orders> ordersByQuantityExample(int quantity) {
		Orders orderProbe = new Orders();
		orderProbe.setQuantity(quantity);

		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withIgnorePaths("orderId", "orderDetails", "unitPrice", "orderDate")
				.withMatcher("quantity", new ExampleMatcher.GenericPropertyMatcher().exact());   //matching for exact value of integer the only method integer matching

		Example<Orders> orderExample = Example.of(orderProbe, matcher);

		return orderRepo.findAll(orderExample);
	}



	/*
	public List<Orders> ordersByDetailsExample(String regexDetails) {
		Orders orderProbe = new Orders();
		orderProbe.setOrderDetails(regexDetails);
		ExampleMatcher matcher = ExampleMatcher.matching() 
											   .withIgnoreNullValues()
											   .withIgnorePaths("orderId", "orderDate", "unitPrice", "quantity")
											   .withStringMatcher(ExampleMatcher.StringMatcher.REGEX);
		Example<Orders> orderExample = Example.of(orderProbe, matcher);
		return orderRepo.findAll(orderExample);
	}
	*/
}
