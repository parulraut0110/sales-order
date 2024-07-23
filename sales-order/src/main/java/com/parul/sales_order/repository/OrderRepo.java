package com.parul.sales_order.repository;

import java.util.Date;



import java.util.List;

import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.parul.sales_order.datasoure.config.MyQueryReWriter;
import com.parul.sales_order.dto.OrderDTO;
import com.parul.sales_order.entity.Orders;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;

import org.springframework.stereotype.Repository;

import com.parul.sales_order.datasoure.config.*;


@Repository
public interface OrderRepo extends JpaRepository<Orders, Integer> {
	
	@PersistenceUnit	
	public EntityManagerFactory emf = null;
	
	@Procedure(name = "get_item")
	public String getItemName(int orderId);
	
	@Procedure(name = "get_order_details")
	public Orders getOrderDetails(int orderId);
	
	/*
	@Query(value = "SELECT * FROM Orders o WHERE o.Price > ?1", nativeQuery = true)   //nativeQuery equal to true implies MySql else would imply JPQL   
	public List<Orders> getOrders(float price);	
	
	@Query(value = "SELECT * FROM Orders o WHERE o.Price > :price", nativeQuery = true)   //:price implies 'named value' i.e provided as the argument price as the method    
	                                                                                      //basically it binds the method parameter to the query parameter
	public List<Orders> getOrders(@Param("price") float price);	
	 */
	
	
	@Query("SELECT o FROM Orders o WHERE o.unitPrice > :priCe")           //This is JPQL where java entity objects are used instead of tables 
	public List<Orders> getOrders(@Param("priCe") float price);
	
	
	//@Query("SELECT o FROM Orders o WHERE o.orderDetails LIKE CONCAT('_', :detailS, '%')")
	@Query("SELECT o FROM Orders o WHERE o.orderDetails LIKE CONCAT('_', '_', :detailS, '%')")
	public List<Orders> getDetails(@Param("detailS") String details);

	public List<Orders> findByUnitPriceAndQuantity(float unitPrice, int quantity);
	
	public List<Orders> findDistinctByorderDetails(String orderDetails);
	
	public List<Orders> findByOrderDateBetween(Date startDate, Date endDate);
		
	public List<Orders> findByOrderDateGreaterThan(Date startDate);
	
	public List<Orders> findByOrderDetailsStartingWith(String orderDetails);
	
	public List<Orders> findByOrderDetailsEndingWith(String orderDetails);
	 
	/*
	@Query(name = "ordersInPriceRange")
	public List<Orders> getOrdersInUnitPrice(@Param("startPrice") float startPrice, @Param("endPrice") float endPrice);
	 */
	  
	@Query(name = "findOrdersByPriceRange", nativeQuery = true)
	public List<OrderDTO> ordersInPriceLimit(@Param("lowerPrice") float startPrice, @Param("upperPrice") float endPrice);
	
	
	public Window<Orders> findFirst10ByUnitPriceGreaterThan(float price, ScrollPosition position);
	
	public Page<Orders> findFirst6ByUnitPriceGreaterThan(float price, Pageable pageable);
	
	@Query(value = "Select * from Orders order by Price ASC",
			nativeQuery = true)
	public List<Orders> findAllOrders();
	
	
	@Query(value = "Select o from Orders o", queryRewriter = MyQueryReWriter.class)
    List<Orders> getAllOrders(Sort sort);
	
	@Query(value = "Select o from Orders o", queryRewriter = PagedQueryRewriter.class)
	Page<Orders> getAllOrdersByPage(Pageable page);
	
	
	
	
}

