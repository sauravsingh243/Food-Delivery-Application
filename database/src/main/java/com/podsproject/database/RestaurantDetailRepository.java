package com.podsproject.database;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface RestaurantDetailRepository extends Repository<RestaurantDetail, Integer>
{
	void save(RestaurantDetail rd);

	List<RestaurantDetail> findAll();

	List<RestaurantDetail> findByRestIdAndItemId(Integer restId, Integer itemId);
}
