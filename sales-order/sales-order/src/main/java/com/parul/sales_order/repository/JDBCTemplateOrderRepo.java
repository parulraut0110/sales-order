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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.SqlReturnUpdateCount;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.core.CallableStatementCallback; 

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

		List<Map<String, Object>> orderList = new ArrayList<>();

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
				@Override
				public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
					Map<String, Object> resultset = new HashMap<>();
					resultset.put("orderid", rs.getInt(1));
					resultset.put("orderdetails", rs.getString(2));
					resultset.put("quantity", rs.getInt(3));
					resultset.put("price", rs.getFloat(4));
					resultset.put("orderdate", rs.getDate(5));
					orderList.add(resultset);
					return resultset;
				}
			}));

			Map<String, Object> result = jdbcTemplate.call(csc, declaredParameters);

			List<Map<String, Object>> resultList = (List<Map<String, Object>>) result.get("orderList");

			return orderList;
	}	


	public Map<String,Object> getMaxOrderPriceByCallableUsingResultSetExtractor(float price) throws SQLException {
		Map<String, Object> maxOrderRow = new HashMap<>();
		CallableStatementCreator csc = new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cs = con.prepareCall("{call getOrdersAbove(?)}", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				cs.setFloat(1, price);
				return cs;
			}};

			List<SqlParameter> declaredParameters = new ArrayList<>();
			declaredParameters.add(new SqlParameter("price", Types.FLOAT)); // to set the IN parameter of the stored procedure
			declaredParameters.add(new SqlReturnResultSet("orderSet", new ResultSetExtractor<Map<String, Object>>() {

				@Override
				public Map<String, Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
					float maxPrice = -1.00F;
					int maxRow = 0;

					while(rs.next()) {
						if(rs.getFloat(4) > maxPrice) {
							maxPrice = rs.getFloat(4);
							maxRow = rs.getRow();
						}
					}
					rs.absolute(maxRow);

					maxOrderRow.put("orderId", rs.getInt(1));
					maxOrderRow.put("orderDetails", rs.getString(2));
					maxOrderRow.put("quantity", rs.getInt(3));
					maxOrderRow.put("unitPrice", rs.getFloat(4));
					maxOrderRow.put("orderDate", rs.getDate(5));

					return maxOrderRow;
				}

			}));
			Map<String, Object> result = jdbcTemplate.call(csc, declaredParameters);
			Map<String, Object> resultList =  (Map<String, Object>)result.get("orderSet");
			return resultList;	
	}	
	
	
	public int updateOrdersByPriceAndGetUpdateCount(float price, float pctChange) throws SQLException, DataAccessException {
		CallableStatementCreator csc = new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cs = con.prepareCall("{call get_update_count(?, ?, ?)}", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				cs.setFloat(1, price);
				cs.setFloat(2, pctChange);
				return cs;
			}};

			List<SqlParameter> declaredParameters = new ArrayList<>();
			declaredParameters.add(new SqlParameter("price", Types.FLOAT)); // to set the IN parameter of the stored procedure
			declaredParameters.add(new SqlParameter("pct_change", Types.NUMERIC));
			declaredParameters.add(new SqlReturnUpdateCount("updateCount"));     //This instructs MySql to return an additional entry 'updateCount' that specifies the number of rows affected by our query
			
			Map<String, Object> result = jdbcTemplate.call(csc, declaredParameters);
			int updateCount = (int)result.get("updateCount");
			return updateCount;	
	}	
	
	
	
	public List<Map<String, Object>> getOrdersByPriceAndInDateBetween(float price, java.sql.Date startDate, java.sql.Date endDate) {
	    String callString = "{call getOrdersByPriceAndInDateBetween(?, ?, ?)}";
	    
	    CallableStatementCallback<List<Map<String, Object>>> callback = new CallableStatementCallback<List<Map<String, Object>>>() {

	        @Override
	        public List<Map<String, Object>> doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
	            cs.setFloat(1, price);
	            cs.setDate(2, new java.sql.Date(startDate.getTime()));
	            cs.setDate(3, new java.sql.Date(endDate.getTime()));
	            
	            cs.execute();
	            ResultSet rs = cs.getResultSet();
	            List<Map<String, Object>> list = new ArrayList<>();
	            
	            while (rs.next()) {
	                Map<String, Object> map = new HashMap<>();
	                map.put("OrderDetails", rs.getString(2)); // Adjust column index as needed
	                map.put("Price", rs.getFloat(4));         // Adjust column index as needed
	                list.add(map);
	            }
	            
	            return list;
	        }
	    };

	    return jdbcTemplate.execute(callString, callback);
	}

	
	
	public void executeSqlStatement() {
		//String sql = "select * from Orders o where o.price > price and new java.sql.Date(o.OrderDate.getTime()) between startDate and endDate"
		//		+ "group by o.OrderDate order by price descending";
		String sql = "insert into Orders(Order_Details, Quantity, Price, Order_Date) values('Goggles', 4, 120.00, '2024-07-14')";
		jdbcTemplate.execute(sql);
	}
	

}


