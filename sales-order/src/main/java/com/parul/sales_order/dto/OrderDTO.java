package com.parul.sales_order.dto;

import java.io.Serializable;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

public class OrderDTO {

    private int orderId;
    private String orderDetails;
    private float orderAmount;
    private int quantity;
    private Date orderDate;

    public OrderDTO(int orderId, String orderDetails, float orderAmount, int quantity, Date orderDate) {
        this.orderId = orderId;
        this.orderDetails = orderDetails;
        this.orderAmount = orderAmount;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }
}
