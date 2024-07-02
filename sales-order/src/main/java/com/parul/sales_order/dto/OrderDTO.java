package com.parul.sales_order.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Component
public class OrderDTO {
	int orderId;
	int quantity;
	String orderDetails;
	float orderPrice;
	Date orderDate;
}
