package com.Delivery.Delivery.controller;

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

import com.Delivery.Delivery.Services.DeliveryService;
import com.Delivery.Delivery.entities.Agent;
import com.Delivery.Delivery.entities.Agentreq;
import com.Delivery.Delivery.entities.CustOrder;
import com.Delivery.Delivery.entities.OrderId;
import com.Delivery.Delivery.entities.OrderStatus;

@RestController
public class MyController {
	
	@Autowired
	public DeliveryService deliveryService;
	
	private final Object obj = new Object();
	@GetMapping("/home")
	public String home() {
		return "This is Delivery Service home";
	}

//Get all orders
//	@GetMapping("/order/all")
//	public List<Orders> getAllOrders(){
//		return this.deliveryService.getAllOrders();
//		}
	
	@GetMapping("/getAllOrder")
	public Map<String, OrderStatus> getAllOrder()
	{
		synchronized (obj)
		{
			return this.deliveryService.getAllOrder();
		}
		
	}
	
	@GetMapping("/restPrice")
	public Map<String, Map<String, Integer>> getAllPrice()	//To check data read from file initialData.txt
	{
		synchronized (obj)
		{
			return this.deliveryService.getAllPrice();
		}
		
	}
	
//	//GET /order/num
//	@GetMapping("/order/{orderId}")
//	public Orders getOrder(@PathVariable String orderId)	
//	{
//		return this.deliveryService.getOrder(orderId);
//	}
	
	//GET /agent/num
	@GetMapping("/agent/{agentId}")
	public ResponseEntity<Agent> getAgentStatus(@PathVariable String agentId)	
	{
		synchronized (obj)
		{
			return this.deliveryService.getAgentStatus(Integer.parseInt(agentId));
		}
		
	}
	
	@PostMapping("/agentSignOut")
	public ResponseEntity<String> agentSignOut(@RequestBody Agentreq a)
	{
//		Agent b;
//		b=this.deliveryService.agentFind(a); //converts object of Agentreq to Agent
		//String var = this.deliveryService.signOut(b);
		//return "hi"
		synchronized (obj)
		{
			return this.deliveryService.signOut(a);
		}
		
	}
	
	
	@PostMapping("/requestOrder")
	public ResponseEntity<OrderId> requestOrder(@RequestBody CustOrder co)
	{
		synchronized (obj)
		{
			return this.deliveryService.requestOrder(co);
		}
		
	}
	
	@PostMapping("/agentSignIn")
	public ResponseEntity<String> agentSignIn(@RequestBody Agentreq a)
	{
//		Agent b;
//		b=this.deliveryService.agentFind(a); //converts object of Agentreq to Agent
		synchronized (obj)
		{
			return this.deliveryService.signIn(a); 
		}
		
		
	}
	
	
	@PostMapping("/orderDelivered")
	public ResponseEntity<String> orderDelivered(@RequestBody OrderId orderId)
	{
		//return orderId.getOrderId();
		//return new ResponseEntity<String>(orderId.getOrderId(),HttpStatus.CREATED);
		synchronized (obj)
		{
			return this.deliveryService.orderDelivered(orderId);
		}
		
	}

	
	@GetMapping("/order/{num}")
	public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable String num)
	{
		synchronized (obj)
		{
			return this.deliveryService.getOrderStatus(num);
		}
		
	}
	
	
	@PostMapping("/reInitialize")
	public ResponseEntity<String> reInitialize() throws IOException
	{
		synchronized (obj)
		{
			return this.deliveryService.reInitialize();
		}
		
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void  reInitialize1() throws IOException
	{
		synchronized (obj)
		{
			this.deliveryService.reInitialize();
		}
		
	}
}