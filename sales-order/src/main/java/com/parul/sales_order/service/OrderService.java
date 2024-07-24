package com.parul.sales_order.service;

import java.util.List;

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
	Orders orderProbe = new Orders();
	orderProbe.setOrderDetails(name);
	ExampleMatcher matcher = ExampleMatcher.matching()
			                               .withIgnoreNullValues()
			                               //.withIgnorePaths("orderId", "unitPrice", "quantity" ,"orderDate")
			                               .withMatcher("orderDetails", new ExampleMatcher.GenericPropertyMatcher().exact());
	Example<Orders> orderExample = Example.of(orderProbe, matcher);
	
	
	return orderRepo.findAll(orderExample);
	
	}
}
