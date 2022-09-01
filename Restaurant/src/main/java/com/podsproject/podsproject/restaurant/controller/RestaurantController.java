package com.podsproject.podsproject.restaurant.controller;



import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.podsproject.podsproject.restaurant.entities.Inventory;
import com.podsproject.podsproject.restaurant.entities.Order;
import com.podsproject.podsproject.restaurant.services.RestaurantServices;

@RestController
public class RestaurantController {
	
	@Autowired
	public RestaurantServices rs;
	
	private final Object obj = new Object();
	
	
	@PostMapping("/acceptOrder")
	public ResponseEntity<String> acceptOrder(@RequestBody Order order) 
	{
		synchronized (obj) 
		{
			return this.rs.acceptOrder(order);
		}
		
	}

	@PostMapping("/refillItem")
	public ResponseEntity<String> refillInventory(@RequestBody Order refill)
	{
		synchronized (obj) 
		{
			return this.rs.refillInventory(refill);
		}
		
	}
	
	@GetMapping("/getInventory/{restID}")
	public Map<Integer, Map<Integer, Inventory>>  getInventory(@PathVariable String restID)
	{
		synchronized (obj) 
		{
			return this.rs.getInventory(Integer.parseInt(restID));
		}
		
	}
	@PostMapping("/reInitialize")
	public ResponseEntity<String> reInitialize() throws IOException
	{
		synchronized (obj) 
		{
			return this.rs.reInitialize();
		}
		
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public ResponseEntity<String> reInitialize1() throws IOException
	{
		synchronized (obj) 
		{
			return this.rs.reInitialize();
		}
		
	}
	
}
