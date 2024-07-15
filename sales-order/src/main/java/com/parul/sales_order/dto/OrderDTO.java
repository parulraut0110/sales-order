package com.parul.sales_order.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
/*
@SqlResultSetMapping(
    name = "orderDTOMapping",
    classes = @ConstructorResult(
        targetClass = OrderDTO.class,
        columns = {
            @ColumnResult(name = "orderId", type = Integer.class),
            @ColumnResult(name = "orderDetails", type = String.class),
            @ColumnResult(name = "orderAmount", type = Float.class),
            @ColumnResult(name = "quantity", type = Integer.class),
            @ColumnResult(name = "orderDate", type = Date.class)
        }
    )
)
*/
public class OrderDTO implements Serializable {
	
	private static final long serialVersionUID = 2L;
    @Id
    private int orderId;
    private String orderDetails;
    private float orderAmount;
    private int quantity;
    private Date orderDate;

}