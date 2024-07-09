package com.parul.sales_order.entity;

import java.util.Date;
import jakarta.persistence.EntityResult;



import org.springframework.stereotype.Component;

import com.parul.sales_order.dto.OrderDTO;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.SqlResultSetMapping;
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

@NamedNativeQuery(
		name = "findOrdersByPriceRange",
				 query = "SELECT "
				            + "o.Order_ID, "
				            + "o.Order_Details as orderDetails, "
				            + "o.Price * o.Quantity AS orderAmount, "
				            + "o.Quantity as quantity, "
				            + "o.Order_Date as orderDate "
				            + "FROM Orders o "
				            + "WHERE o.Price * o.Quantity > :lowerPrice "
				            + "AND o.Price * o.Quantity < :upperPrice",
				 resultClass = OrderDTO.class          
		
		//resultSetMapping = "com.parul.sales-orders.dto.OrderDTO.entityResultSetMapping"		
		)

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
