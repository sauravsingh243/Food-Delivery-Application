
//Service layer for delivery service
package com.Delivery.Delivery.Services;

import java.io.IOException;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.Delivery.Delivery.entities.Agent;
import com.Delivery.Delivery.entities.Agentreq;
import com.Delivery.Delivery.entities.CustOrder;
import com.Delivery.Delivery.entities.OrderId;
import com.Delivery.Delivery.entities.OrderStatus;


public interface DeliveryService {

	public ResponseEntity<Agent>  getAgentStatus(int agentId);
	
	public ResponseEntity<String> signOut(Agentreq agentId);
	
	public ResponseEntity<String> signIn(Agentreq agentId);
	
	public Agent agentFind(Agentreq agentId);
	
	public Map<String, Map<String, Integer>> getAllPrice();
	
	public ResponseEntity<String> reInitialize() throws IOException;
	
	public ResponseEntity<OrderId> requestOrder(CustOrder co);
	
	public ResponseEntity<String> orderDelivered(OrderId orderId);
	
	public ResponseEntity<OrderStatus> getOrderStatus(String num);
	
	public Map<String, OrderStatus> getAllOrder();
}
