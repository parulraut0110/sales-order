package com.parul.sales_order.dto;

import java.util.Date;
import jakarta.persistence.FieldResult;
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
@SqlResultSetMapping(
		name = "entityResultSetMapping",
		entities = @EntityResult(
				entityClass = OrderDTO.class,
				fields = {
						@FieldResult(name = "orderId", column = "o.Order_ID"),
						@FieldResult(name = "orderDetails", column = "orderDetails"),
						@FieldResult(name = "orderAmount", column = "orderAmount"),
						@FieldResult(name = "quantity", column = "quantity"),
						@FieldResult(name = "orderDate", column= "orderDate")
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


