package com.parul.sales_order.repository;

import java.util.Date;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.parul.sales_order.entity.Orders;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;

import org.springframework.stereotype.Repository;


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

	List<Orders> findByUnitPriceAndQuantity(float unitPrice, int quantity);
	
	List<Orders> findDistinctByorderDetails(String orderDetails);
	
	List<Orders> findByOrderDateBetween(Date startDate, Date endDate);
		
	List<Orders> findByOrderDateGreaterThan(Date startDate);
	
	List<Orders> findByOrderDetailsStartingWith(String orderDetails);
	
	List<Orders> findByOrderDetailsEndingWith(String orderDetails);
	 
	/*
	@Query(name = "ordersInPriceRange")
	public List<Orders> getOrdersInUnitPrice(@Param("startPrice") float startPrice, @Param("endPrice") float endPrice);
	 */
	
	@Query(name = "findOrdersByPriceRange", nativeQuery = true)
	public List<Orders> ordersInPriceLimit(@Param("lowerPrice") float startPrice, @Param("upperPrice") float endPrice);
	
}

