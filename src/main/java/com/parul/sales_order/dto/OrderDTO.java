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
public class OrderDTO {
	
    @Id
    private int orderId;
    private String orderDetails;
    private float orderAmount;
    private int quantity;
    private Date orderDate;
   

}