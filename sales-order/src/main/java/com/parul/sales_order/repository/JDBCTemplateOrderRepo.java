package com.parul.sales_order.repository;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.parul.sales_order.entity.Orders;

@Repository
public class JDBCTemplateOrderRepo {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public void performInserts() {
		String insertSql1 = "insert into Orders(Order_Details, Quantity, Price, Order_Date) values('HpLaptop', 85000.00, 1, '2024-07-14')";
		String insertSql2 = "insert into Orders(Order_Details, Quantity, Price, Order_Date) values('RO filter', 15000.00, 1, '2024-07-14')";

		jdbcTemplate.batchUpdate(insertSql1, insertSql2);

	}


//batchUpdate(String sql, Collection<T> batchArgs, int batchSize, ParameterizedPreparedStatementSetter<T> pss)

	public void performInsertsByArgs(List<Orders> batchArgs) {
		String insertSql = "insert into Orders(Order_Details, Quantity, Price, Order_Date) values(?, ?, ?, ?)";
		
		jdbcTemplate.batchUpdate(insertSql, batchArgs, batchArgs.size(), 
								(ps, orders) -> {        //ps is actually the prepared statement insertSql that is passed internally by the spring framework to the undefined method setValues of the ParameterizedPreparedStatementSetter  
									ps.setString(1, orders.getOrderDetails());
									ps.setInt(2, orders.getQuantity());
									ps.setFloat(3, orders.getUnitPrice());
									ps.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
								});
	}
	
//batchUpdate(String sql, List<Object[]> batchArgs)	
	
	public void performBatchInsertByArgs(List<Orders> batchArgs) {
		String insertSql = "insert into Orders(Order_Details, Quantity, Price, Order_Date) values(?, ?, ?, ?)";
		
		List<Object[]> args = batchArgs.stream()
							  .map(o -> new Object[] { o.getOrderDetails(), o.getQuantity(), o.getUnitPrice(), o.getOrderDate() })
							  .toList();
		
		jdbcTemplate.batchUpdate(insertSql, args);

	}
}
