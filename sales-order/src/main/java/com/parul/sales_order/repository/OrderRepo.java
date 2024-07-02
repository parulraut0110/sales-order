package com.parul.sales_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import com.parul.sales_order.entity.Orders;

public interface OrderRepo extends JpaRepository<Orders, Integer> {
	
	@Procedure(name = "get_item")
	public String getItemName(int orderId);
	
	
	@Procedure(name = "get_order_details")
	public Orders getOrderDetails(int orderId);
}
