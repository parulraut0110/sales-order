package com.parul.sales_order.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
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


	//batchUpdate(String sql, Collection<T> batchArgs, int batchSize, ParameterizedPreparedStatementSetter<T> pss)  T is the parameter, the object from where the field values are extracted to set the place holders

	public int[][] performInsertsByArgs(List<Orders> batchArgs) {
		String insertSql = "insert into Orders(Order_Details, Quantity, Price, Order_Date) values(?, ?, ?, ?)";

		int[][] updateStats = jdbcTemplate.batchUpdate(insertSql, batchArgs, batchArgs.size(), 
				(ps, orders) -> {        //ps is actually the prepared statement insertSql that is passed internally by the spring framework to the undefined method setValues of the ParameterizedPreparedStatementSetter  
					ps.setString(1, orders.getOrderDetails());
					ps.setInt(2, orders.getQuantity());
					ps.setFloat(3, orders.getUnitPrice());
					ps.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
				});
		return updateStats;

	}

	//batchUpdate(String sql, List<Object[]> batchArgs)	

	public void performBatchInsertByArgs(List<Orders> batchArgs) {
		String insertSql = "insert into Orders(Order_Details, Quantity, Price, Order_Date) values(?, ?, ?, ?)";

		List<Object[]> args = batchArgs.stream()
				.map(o -> new Object[] { o.getOrderDetails(), o.getQuantity(), o.getUnitPrice(), o.getOrderDate() })
				.toList();

		jdbcTemplate.batchUpdate(insertSql, args);

	}

	//batchUpdate(String sql, BatchPreparedStatementSetter pss)	

	public void performBatchInsertByBatchSetter(List<Orders> batchArgs) {
		String insertSql = "insert into Orders(Order_Details, Quantity, Price, Order_Date) values(?, ?, ?, ?)";

		List<Object[]> args = batchArgs.stream()
				.map(o -> new Object[] { o.getOrderDetails(), o.getQuantity(), o.getUnitPrice(), o.getOrderDate() })
				.toList();

		jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				if(i == 0) {
					ps.setString(1, "Water bottle");
					ps.setInt(2, 3);
					ps.setFloat(3, 300.00F);
					ps.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
				}

				if(i == 1) {
					ps.setString(1, "Wine");
					ps.setInt(2, 2);
					ps.setFloat(3, 3000.00F);
					ps.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
				}

				/*
					if(i == 2) {
						ps.setString(1, "Champagneios");
						ps.setInt(2, 1);
						ps.setFloat(3, 7000.00F);
						ps.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
					}
				 */

				if(i == 2) {
					ps.setString(1, (String)args.get(i)[0]);
					ps.setInt(2, 1);
					ps.setFloat(3, 1111.11F);
					ps.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
				}
			}

			public int getBatchSize() {
				return batchArgs.size();
			}
		});
	}

	//batchUpdate(String sql, List<Object[]> batchArgs, int[] argTypes)		 //The third parameter argTypes implies the java.sql.Type of each arguments passed 

	public void performBatchInsertByArgAndTypes(List<Orders> batchArgs) {    
		String insertSql = "insert into Orders(Order_Details, Quantity, Price, Order_Date) values(?, ?, ?, ?)";

		List<Object[]> args = batchArgs.stream()
				.map(o -> new Object[] { o.getOrderDetails(), o.getQuantity(), o.getUnitPrice(), o.getOrderDate() })
				.toList();

		int[] argTypes = {Types.VARCHAR, Types.INTEGER, Types.FLOAT, Types.DATE};

		jdbcTemplate.batchUpdate(insertSql, args, argTypes); 			
	}
	
//batchUpdate(PreparedStatementCreator psc, BatchPreparedStatementSetter pss, KeyHolder generatedKeyHolder)
	
	public void performBatchInsertWithKey(List<Orders> batchArgs) throws SQLException {

		List<Object[]> args = batchArgs.stream()
				.map(o -> new Object[] { o.getOrderDetails(), o.getQuantity(), o.getUnitPrice(), o.getOrderDate() })
				.toList();


		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return null;
			}
			
		};
		
	}
}
