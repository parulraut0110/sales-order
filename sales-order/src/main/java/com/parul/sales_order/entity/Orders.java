package com.parul.sales_order.entity;

import java.util.Date;


import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "Orders")
@NamedStoredProcedureQuery(                           //The stored procedure mapping is shown in the Entity class 
		name = "get_item", 
		procedureName = "get_item",
		resultClasses = String.class,
		parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "orderId", type = Integer.class)
		}
		)
@NamedStoredProcedureQuery(                           //The stored procedure mapping is shown in the Entity class 
		name = "get_order_details", 
		procedureName = "get_order_details",
		resultClasses = Orders.class,
		parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "orderId", type = Integer.class)
		}
		)
/*
@NamedQueries({
    @NamedQuery(
        name = "OrdersInUnitPriceRange",
        query = "SELECT o FROM Orders o WHERE o.unitPrice > :startPrice AND o.unitPrice < :endPrice"
    )
})
*/
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_ID")
    private int orderId;

    @Column(name = "Order_Details")
    private String orderDetails;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "Price")
    private float unitPrice;

    @Column(name = "Order_Date")
    @Temporal(TemporalType.DATE)
    private Date orderDate;
}
