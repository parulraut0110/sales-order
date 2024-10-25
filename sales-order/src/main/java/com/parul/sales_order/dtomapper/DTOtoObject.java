package com.parul.sales_order.dtomapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.parul.sales_order.dto.OrderDTO;
import com.parul.sales_order.entity.Orders;

public class DTOtoObject {
	@Autowired
	Orders orders;
	
    public Orders DTOtoObject(OrderDTO orderDTO) {
    	orders.setOrderId(orderDTO.getOrderId());
    	orders.setOrderDetails(orderDTO.getOrderDetails());
    	orders.setQuantity(orderDTO.getQuantity());
    	orders.setUnitPrice(orderDTO.getOrderPrice() / orderDTO.getQuantity());
    	orders.setOrderDate(orderDTO.getOrderDate());
    	
    	return orders;
    }
}
