package com.parul.sales_order.dtomapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.parul.sales_order.dto.OrderDTO;
import com.parul.sales_order.entity.Orders;

public class OrderToDto {
	@Autowired
    OrderDTO orderDTO;
	public OrderDTO orderToDto(Orders order) {
		orderDTO.setOrderId(order.getOrderId());
		orderDTO.setOrderDetails(order.getOrderDetails());
		orderDTO.setQuantity(order.getQuantity());
		orderDTO.setOrderAmount(order.getUnitPrice() * order.getQuantity());;
		orderDTO.setOrderDate(order.getOrderDate());
		return orderDTO;
	}
}
