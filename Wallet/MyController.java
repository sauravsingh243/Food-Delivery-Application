package com.Delivery.Delivery.controller;

import java.io.IOException;
import java.util.List;
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

import com.Delivery.Delivery.Services.DeliveryService;
import com.Delivery.Delivery.entities.Agent;
import com.Delivery.Delivery.entities.Agentreq;
import com.Delivery.Delivery.entities.Orders;

@RestController
public class MyController {
	
	@Autowired
	public DeliveryService deliveryService;
	
	@GetMapping("/home")
	public String home() {
		return "This is Delivery Service home";
	}

	//Get all orders
	@GetMapping("/order/all")
	public List<Orders> getAllOrders(){
		return this.deliveryService.getAllOrders();
		}
	
	@GetMapping("/restPrice")
	public Map<String, Map<String,Integer>> getAllPrice()	//To check data read from file initialData.txt
	{
		return this.deliveryService.getAllPrice();
	}
	
	//GET /order/num
	@GetMapping("/order/{orderId}")
	public Orders getOrder(@PathVariable String orderId)	
	{
		return this.deliveryService.getOrder(orderId);
	}
	
	//GET /order/num
	@GetMapping("/agent/{agentId}")
	public ResponseEntity<Agent> getAgentStatus(@PathVariable String agentId)	
	{
		return this.deliveryService.getAgentStatus(agentId);
	}
	
	@PostMapping("/agentSignOut")
	public ResponseEntity<String> agentSignOut(@RequestBody Agentreq a)
	{
		Agent b;
		b=this.deliveryService.agentFind(a); //converts object of Agentreq to Agent
		return this.deliveryService.signOut(b);
	}
	
	@PostMapping("/requestOrder")
	public ResponseEntity<String> requestOrder(@RequestBody custOrder co)
	{
		
	}
	
	
	@PostMapping("/agentSignIn")
	public ResponseEntity<String> agentSignIn(@RequestBody Agentreq a)
	{
		Agent b;
		b=this.deliveryService.agentFind(a); //converts object of Agentreq to Agent
		return this.deliveryService.signIn(b); 
		
	}

	@PostMapping("/reInitialize")
	public ResponseEntity<Map<String, String>> reInitialize() throws IOException
	{
		return this.deliveryService.reInitialize();
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void  reInitialize1() throws IOException
	{
		this.deliveryService.reInitialize();
	}
}
