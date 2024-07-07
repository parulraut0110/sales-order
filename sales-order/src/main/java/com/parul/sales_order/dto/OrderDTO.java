package com.parul.sales_order.dto;

import java.util.Date;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.EntityResult;
import jakarta.persistence.ColumnResult;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
@NamedNativeQuery(
	name = "findByOrderPriceRange",
	query = "SELECT"
			+ "    r.Order_ID as orderId,"
			+ "    r.Order_Details as orderDetails,"
			+ "    r.Order_Amount as orderAmount,"
			+ "    r.Quantity as quantity,"
			+ "    r.Order_Date as orderDate"
			+ "FROM"
			+ "    (SELECT"
			+ "         o.Order_ID,"
			+ "         o.Order_Details,"
			+ "         o.Order * o.Quantity AS OrderAmount,"
			+ "         o.Quantity,"
			+ "         o.Order_Date"
			+ "     FROM"
			+ "         Orders o"
			+ "     WHERE"
			+ "         o.Price * o.Quantity > :lowerPrice"
			+ "         AND o.Price * o.Quantity < :upperPrice) r",
	resultClass = OrderDTO.class
)
@SqlResultSetMapping(
	    name = "findByOrderPriceRange",
	    classes = @ConstructorResult(
	        targetClass = OrderDTO.class,
	        columns = {
	            @ColumnResult(name = "orderId", type = Integer.class),
	            @ColumnResult(name = "orderDetails", type = String.class),
	            @ColumnResult(name = "orderAmount", type = Float.class),
	            @ColumnResult(name = "quantity", type = Integer.class),
	            @ColumnResult(name = "orderDate", type = java.util.Date.class)
	        }
	    )
	)
public class OrderDTO {
	int orderId;
	String orderDetails;
	float orderAmount;
	int quantity;
	Date orderDate;
	
}


