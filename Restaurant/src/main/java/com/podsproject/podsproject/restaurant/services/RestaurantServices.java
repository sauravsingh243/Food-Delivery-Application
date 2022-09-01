package com.podsproject.podsproject.restaurant.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.podsproject.podsproject.restaurant.entities.Inventory;
import com.podsproject.podsproject.restaurant.entities.Order;

public interface RestaurantServices {

	public ResponseEntity<String> acceptOrder(Order order);
	
	public ResponseEntity<String> reInitialize() throws IOException;
	
	public Map<Integer, Map<Integer, Inventory>>  getInventory(Integer restID);
	
	public ResponseEntity<String> refillInventory(Order refill);
}
