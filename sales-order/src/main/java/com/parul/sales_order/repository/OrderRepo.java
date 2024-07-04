package com.parul.sales_order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import com.parul.sales_order.entity.Orders;

import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepo extends JpaRepository<Orders, Integer> {
	
	@Procedure(name = "get_item")
	public String getItemName(int orderId);
	
	
	@Procedure(name = "get_order_details")
	public Orders getOrderDetails(int orderId);
	
	@Query("SELECT o FROM Orders o WHERE o.unitPrice> ?1")
	public List<Orders> getOrders(float price);	
}

