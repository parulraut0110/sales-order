package com.parul.sales_order.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.stereotype.Repository;

import com.parul.sales_order.entity.Orders;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

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

		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement("insert into Orders(Order_Details, Quantity, Price, Order_Date) values(?, ?, ?, ?)", new String[]{"Order_Id"});
				return ps;
			}

		};

		BatchPreparedStatementSetter bpss = new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, batchArgs.get(i).getOrderDetails());
				ps.setInt(2, batchArgs.get(i).getQuantity());
				ps.setFloat(3, batchArgs.get(i).getUnitPrice());
				ps.setDate(4, new java.sql.Date(new java.util.Date().getTime()));	
			}

			@Override
			public int getBatchSize() {
				return batchArgs.size();
			}
		};
		
		KeyHolder keyHolder = new GeneratedKeyHolder();	
		
		jdbcTemplate.batchUpdate(psc, bpss, keyHolder);
		
		List<Map<String, Object>> keyList = keyHolder.getKeyList();
		
		for(Map<String, Object> key : keyList) 
			for(Map.Entry<String, Object> entry : key.entrySet()) 
				System.out.println("Generated Key : " + entry.getKey() + " " + entry.getValue());
			
	}
	
//public Map<String,Object> call(CallableStatementCreator csc, List<SqlParameter> declaredParameters)	
	
	public List<Orders> getOrdersAboveByCallable(float price) throws SQLException {
		CallableStatementCreator csc = new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cs = con.prepareCall("{call getOrdersAbove(?)}");
				cs.setFloat(1, price);
				//cs.registerOutParameter(2, Types.REF_CURSOR);
				return cs;
			}};
			
			List<SqlParameter> declaredParameters = new ArrayList<>();
			declaredParameters.add(new SqlParameter(Types.FLOAT));
			//declaredParameters.add(new SqlOutParameter("orders", Types.FLOAT));

			Map<String, Object> result = jdbcTemplate.call(csc, declaredParameters);
			
			//jdbc default naming for 1st resultset  
			List<Map<String, Object>> resultList = (List<Map<String, Object>>) result.get("#result-set-1");
			
			List<Orders> orders = new ArrayList<>();
			
			for(Map<String, Object> row : resultList) {
				Orders order = new Orders();
				order.setOrderId((int)row.get("Order_ID"));
				order.setOrderDetails((String)row.get("Order_Details"));
				order.setQuantity((int)row.get("Quantity"));
				order.setUnitPrice((float)row.get("Price"));
				order.setOrderDate((java.util.Date)row.get("Order_Date"));
				
				orders.add(order);
			}
			return orders;
	}	
	
	public List<Map<String,Object>> getOrdersAboveByCallableUsingSqlResultSet(float price) throws SQLException {
		CallableStatementCreator csc = new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cs = con.prepareCall("{call getOrdersAbove(?)}");
				cs.setFloat(1, price);
				return cs;
			}};
			
			List<SqlParameter> declaredParameters = new ArrayList<>();
			declaredParameters.add(new SqlParameter("price", Types.FLOAT)); // to set the IN parameter of the stored procedure
			declaredParameters.add(new SqlReturnResultSet("orderList", new RowMapper<Map<String, Object>>() {
				List<Map<String, Object>> resultset = new ArrayList<>();
			    @Override
			    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
			        Map<String, Object> resultset = new HashMap<>();
			        resultset.put("orderid", rs.getInt(1));
			        resultset.put("orderdetails", rs.getString(2));
			        resultset.put("quantity", rs.getInt(3));
			        resultset.put("price", rs.getFloat(4));
			        resultset.put("orderdate", rs.getDate(5));
			        //System.out.println("details#: " + resultset.get("orderdetails"));
			        return resultset;
			    }
			}));

			Map<String, Object> result = jdbcTemplate.call(csc, declaredParameters);
			
			List<Map<String, Object>> resultList = (List<Map<String, Object>>) result.get("orderList");

			return resultList;
	}	
	
	
	
	}