package com.parul.sales_order.entity;

import java.io.Serializable;
import java.util.Date;

import com.parul.sales_order.dto.OrderDTO;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Orders")
@NamedStoredProcedureQuery(
    name = "get_item", 
    procedureName = "get_item",
    resultClasses = String.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "orderId", type = Integer.class)
    }
)
@NamedStoredProcedureQuery(
    name = "get_order_details", 
    procedureName = "get_order_details",
    resultClasses = Orders.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "orderId", type = Integer.class)
    }
)

@SqlResultSetMapping(
	    name = "orderDTOMapping",
	    classes = @ConstructorResult(
	        targetClass = Orders.class,
	        columns = {
	            @ColumnResult(name = "orderId", type = Integer.class),
	            @ColumnResult(name = "orderDetails", type = String.class),
	            @ColumnResult(name = "unitPrice", type = Float.class),
	            //@ColumnResult(name = "orderAmount", type = Float.class),
	            @ColumnResult(name = "quantity", type = Integer.class),
	            @ColumnResult(name = "orderDate", type = Date.class)
	        }
	    )
	)

@NamedNativeQuery(
    name = "findOrdersByPriceRange",
    query = "SELECT * "
    /*
            + "order_id as orderId, "
            + "Order_Details as orderDetails, "
            //+ "Price * Quantity AS orderAmount, "
            + "Quantity as quantity, "
            + "Price as unitPrice, "
            + "Order_Date as orderDate "
     */        
            + "FROM Orders "
            + "WHERE Price * Quantity > :lowerPrice "
            + "AND Price * Quantity < :upperPrice",
           
    //resultSetMapping = "orderDTOMapping",
    resultClass = Orders.class
)


/*
@NamedNativeQuery(
	    name = "findOrdersByPriceRange",
	    query = "SELECT * "
	    
	            + "o.Order_ID as orderId, "
	            + "o.Order_Details as orderDetails, "
	            + "o.Price as price, "
	           // + "o.Price * o.Quantity as orderAmount, "
	            + "o.Quantity as quantity, "
	            + "o.Order_Date as orderDate "
	            
	            + "FROM Orders o "
	            + "WHERE o.Price * o.Quantity > :lowerPrice "
	            + "AND o.Price * o.Quantity < :upperPrice",
	          
	    //resultSetMapping = "orderDTOEntityMapping"
	    resultClass = Orders.class        
	)
*/


public class Orders implements Serializable {
	
	 private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
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