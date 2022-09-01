package com.podsproject.database;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface OrderStatusRepository extends Repository<OrderStatus, Integer>
{
	OrderStatus findById(Integer orderId);
	
	void save(OrderStatus os);
	
	void deleteAll();
	
	List<OrderStatus> findByStatus(String status);
	
	OrderStatus findFirstByStatusOrderByOrderIdAsc(String status);

	List<OrderStatus> findAll();
}
