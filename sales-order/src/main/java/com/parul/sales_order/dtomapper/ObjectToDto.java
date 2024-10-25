package com.parul.sales_order.dtomapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.parul.sales_order.dto.OrderDTO;
import com.parul.sales_order.entity.Orders;

public class ObjectToDto {
	@Autowired
    OrderDTO orderDTO;
	public OrderDTO ObjectToDto(Orders order) {
		orderDTO.setOrderId(order.getOrderId());
		orderDTO.setOrderDetails(order.getOrderDetails());
		orderDTO.setQuantity(order.getQuantity());
		orderDTO.setOrderPrice(order.getUnitPrice() * order.getQuantity());
		orderDTO.setOrderDate(order.getOrderDate());
		return orderDTO;
	}
}
