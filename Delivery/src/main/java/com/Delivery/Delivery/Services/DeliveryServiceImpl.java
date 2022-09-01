// Implementation of delivery services
package com.Delivery.Delivery.Services;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.Delivery.Delivery.entities.Agent;
import com.Delivery.Delivery.entities.Agentreq;
import com.Delivery.Delivery.entities.CustOrder;
import com.Delivery.Delivery.entities.OrderId;
import com.Delivery.Delivery.entities.OrderStatus;
import com.Delivery.Delivery.entities.RestaurantDetail;
import com.Delivery.Delivery.entities.RestaurantOrder;
import com.Delivery.Delivery.entities.WalletDelivery;

@Service
public class DeliveryServiceImpl implements DeliveryService {
	
	// In memory data structures for delivery service implementation
	int orderId;
	LinkedHashMap<String, String> agentMap;
	Map<String, Map<String, Integer>> priceMap;
	Map<String,Integer> itemPrice;
	LinkedHashMap<String, OrderStatus> orderMap;
	public  DeliveryServiceImpl() {
		orderMap = new LinkedHashMap<>();
	}
	
	// Getting price of all the restuarant, Item, price 
	@Override
	public Map<String, Map<String, Integer>> getAllPrice()
	{
		return priceMap;
	}
	
	//to convert Agentreq obj to Agent obj
	@Override
	public Agent agentFind(Agentreq agentReqObj)	
	{
		Agent result= new Agent(0,null);
		for (Map.Entry<String,String> entry : agentMap.entrySet())
		{
			String var=entry.getKey();
			if(Integer.parseInt(var) == agentReqObj.agentId)
			{
				result.setAgentId(Integer.parseInt(entry.getKey()));
				result.setStatus(entry.getValue());
				break;
			}
		}
		return result;
	}
	
	//Function for get agent status
	@Override
	public ResponseEntity<Agent> getAgentStatus(int agentId) //To convert int to Agent Obj
	{
		Agent result = new Agent();
		String dbUrl = "http://abhidatabase:8080/agentDetail";
//		String dbUrl = "http://localhost:8080/agentDetail";
//		String dbUrl = "http://host.docker.internal:8080/agentDetail";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Integer> parameter = new HashMap<>();
		parameter.put("agentId", agentId);
		HttpEntity<Map<String, Integer>> he = new HttpEntity<>(parameter, headers);
		ResponseEntity<String> dbresponse = restTemplate.postForEntity(dbUrl, he, String.class);	
		result.setAgentId(agentId);
		result.setStatus(dbresponse.getBody());
		return new ResponseEntity<Agent>(result,HttpStatus.OK);
	}
	
	//Function for agent signOut
//	@Override
//	public ResponseEntity<String> signOut(Agent a)
//	{
//		for (Map.Entry<String,String> entry : agentMap.entrySet())
//		{
//			if(entry.getKey().equals(String.valueOf(a.getAgentId())))
//			{
//				if(entry.getValue().equals("available"))
//				{
//					agentMap.put(entry.getKey(), "signed-out");
//				}	
//				break;
//			}
//		}
//		return new ResponseEntity<String>(HttpStatus.CREATED);
//	}
	
	//Function for agent signOut
	@Override
	public ResponseEntity<String> signOut(Agentreq a)
	{
		String dbUrl = "http://abhidatabase:8080/agentSignOut";
//		String dbUrl = "http://localhost:8080/agentSignOut";
//		String dbUrl = "http://host.docker.internal:8080/agentSignOut";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Integer> parameter = new HashMap<>();
		parameter.put("agentId", a.getAgentId());
		HttpEntity<Map<String, Integer>> he = new HttpEntity<>(parameter, headers);
		restTemplate.postForEntity(dbUrl, he, String.class);	
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
//	//Function for agent signIn
//	@Override
//	public ResponseEntity<String> signIn(Agent a)
//	{
//		
//		for (Map.Entry<String,String> entry : agentMap.entrySet())
//		{
//			if(entry.getKey().equals(String.valueOf(a.getAgentId())))
//			{
//				if(entry.getValue().equals("available") || entry.getValue().equals("unavailable"))
//				{
//					return new ResponseEntity<String>(HttpStatus.CREATED);
//				}
//				else
//				{
//					agentMap.put(entry.getKey(), "available");
//					for(Map.Entry<String, OrderStatus> entry1 : orderMap.entrySet())
//					{
//						OrderStatus os = entry1.getValue();
//						if(os.getStatus().equals("unassigned"))
//						{
//							os.setStatus("assigned");
//							os.setAgentId(a.getAgentId());
//							orderMap.put(entry1.getKey(), os);
//							agentMap.put(entry.getKey(), "unavailable");
//							break;
//						}
//					}
//				}
//				break;
//			}
//			
//		}
//		return new ResponseEntity<String>(HttpStatus.CREATED);
//	}

	//Function for agent signIn
	@Override
	public ResponseEntity<String> signIn(Agentreq a)
	{
		String dbUrl = "http://abhidatabase:8080/agentSignIn";
//		String dbUrl = "http://localhost:8080/agentSignIn";
//		String dbUrl = "http://host.docker.internal:8080/agentSignIn";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Integer> parameter = new HashMap<>();
		parameter.put("agentId", a.getAgentId());
		HttpEntity<Map<String, Integer>> he = new HttpEntity<>(parameter, headers);
		restTemplate.postForEntity(dbUrl, he, String.class);	
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
//	
//	//Reinitialize from the input file
//	public ResponseEntity<Map<String, String>> reInitialize() throws IOException
//	{		
//	    FileInputStream f = null;
//	    try 
//	    {
//	    	String path = new File("").getAbsolutePath(); //Enter path of initial_data.txt here
//	    	File file = new File(path + "/initialData.txt");
//	    	f = new FileInputStream(file);
//	    } 
//	    catch (FileNotFoundException e) 
//	    {
//	    	e.printStackTrace();
////	        return ResponseEntity.status(201).body("");
//	    }
//	    BufferedReader br = new BufferedReader(new InputStreamReader(f));
//	    String Line;
//	    agentMap = new LinkedHashMap<>();
//	    priceMap = new HashMap<>();
//	    itemPrice = new HashMap<>();
//	    String restID = null;
//	    //skipping till first ****
//	    while ((Line = br.readLine()) != null)   
//	    {
//	    	Line = Line.strip();
//	    	if (Line.charAt(0) == '*')
//	    	{
//	    		priceMap.put(restID, itemPrice);
//	    		break;
//	        }
//	    	else 
//	    	{
//	    		String[] var=Line.split(" ");
//	    		if(var.length == 2) //reading restaurant and number of items 
//	    		{
//	    			if(restID == null)
//	    			{
//	    				
//	    				restID = var[0];
//			    		itemPrice = new HashMap<>(); 
//		    		}
//		    		else
//		    		{
//		    			priceMap.put(restID, itemPrice);
//			    		restID = var[0];
//			    		itemPrice = new HashMap<>();
//		    		}
//		    		
//		    	}
//		    	else if(var.length == 3) //reading price of items 
//		    	{
//		    		itemPrice.put(var[0], Integer.parseInt(var[1]));
//		    	}
//	    	}
//	    }
//	    while ((Line = br.readLine()) != null)   //reading all agent ids
//	    {
//	    	Line = Line.strip();
//	    	if (Line.charAt(0) == '*')
//	    	{
//	    		break;
//	    	}
//	    	agentMap.put(Line, "signed-out"); //Initializing agent status to sign-out
//	    }
//	    
//	    //------------------------------Code to remove all orders----------------------
//	    
//	    f.close();
//	    orderId = 1000;
//	    orderMap.clear();
//	    return new ResponseEntity<Map<String, String>>(agentMap,HttpStatus.CREATED);
//	}
	
	
	//Reinitialize from the input file
	public ResponseEntity<String> reInitialize() throws IOException
	{		
		
		
//		String dbUrl = "http://host.docker.internal:8080/removeAllOrder";
//		String dbUrl = "http://localhost:8080/removeAllOrder";
		String dbUrl = "http://abhidatabase:8080/removeAllOrder";
		RestTemplate restTemplate = new RestTemplate();
		try {
			 restTemplate.getForEntity(dbUrl, String.class);	
		}
		catch(HttpClientErrorException e)
		{
			
		}
//		dbUrl = "http://host.docker.internal:8080/removeAllAgent";
//		dbUrl = "http://localhost:8080/removeAllAgent";
		dbUrl = "http://abhidatabase:8080/removeAllAgent";		
		try
		{
			restTemplate.getForEntity(dbUrl, String.class);	
		}
		catch(HttpClientErrorException e)
		{
			
		}
		
	    FileInputStream f = null;
	    try 
	    {
	    	String path = new File("").getAbsolutePath(); //Enter path of initial_data.txt here
	    	File file = new File(path + "/initialData.txt");
	    	f = new FileInputStream(file);
	    } 
	    catch (FileNotFoundException e) 
	    {
	    	e.printStackTrace();
//	        return ResponseEntity.status(201).body("");
	    }
	    BufferedReader br = new BufferedReader(new InputStreamReader(f));
	    String Line;
	    agentMap = new LinkedHashMap<>();
	    priceMap = new HashMap<>();
	    itemPrice = new HashMap<>();
	    int restId = 0;
	    int numberofItem = 0;
	    //skipping till first ****
	    while ((Line = br.readLine()) != null)   
	    {
	    	Line = Line.strip();
	    	if (Line.charAt(0) == '*')
	    	{
	    		break;
	    	}
	    		
	    	String[] var = Line.split(" ");
	    	restId = Integer.parseInt(var[0]);
	    	numberofItem = Integer.parseInt(var[1]);
	    	for(int i = 0; i < numberofItem; i++)
	    	{
	    		Line = br.readLine();
	    		var = Line.split(" ");
	    		int itemId = Integer.parseInt(var[0]);
	    		int price = Integer.parseInt(var[1]);
//	    		dbUrl = "http://localhost:8080/addRestaurant";
	    		dbUrl = "http://abhidatabase:8080/addRestaurant";
	    		RestaurantDetail rd = new RestaurantDetail(restId, itemId, price);
	    		restTemplate = new RestTemplate();
	    		HttpHeaders header = new HttpHeaders();
		    	header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<RestaurantDetail> entity = new HttpEntity<>(rd, header);
		    	restTemplate.postForEntity(dbUrl, entity, String.class);	
	    	}
	    	
	    	
	    	
	    }
	    while ((Line = br.readLine()) != null)   //reading all agent ID
	    {
	    	Line = Line.strip();
	    	if (Line.charAt(0) == '*')
	    	{
	    		break;
	    	}
	    	Agent ag = new Agent(Integer.parseInt(Line), "signed-out");
//	    	dbUrl = "http://host.docker.internal:8080/addAgent";
//	    	dbUrl = "http://localhost:8080/addAgent";
	    	dbUrl = "http://abhidatabase:8080/addAgent";
	    	HttpHeaders header = new HttpHeaders();
	    	header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Agent> entity = new HttpEntity<>(ag, header);
			restTemplate.postForEntity(dbUrl, entity, String.class);	
	    }
	    
	    //------------------------------Code to remove all orders----------------------
	    
	    f.close();
	    orderId = 1000;
	    orderMap.clear();
	    return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	
	
	
	
	
	//JSON payload of the form {“custId”: num, “restId”: x, “itemId”: y, “qty”: z}
//	@Override
//	public ResponseEntity<OrderId> requestOrder(CustOrder co) 
//	{
//		String cost = "";
//		boolean deducted = false;
//		//Calculating the total billing amount of the order
//		for(Map.Entry<String, Map<String, Integer>> entry : priceMap.entrySet())
//		{
//			if(Integer.parseInt(entry.getKey()) == co.getRestID())//matching restid in order with in memory rest id
//			{
//				Map<String, Integer> varTemp = entry.getValue();
//				for(Map.Entry<String, Integer> entry1 : varTemp.entrySet())
//				{
//					if(Integer.parseInt(entry1.getKey()) == co.getItemID())
//					{
//						cost += String.valueOf(co.getQuantity()*entry1.getValue());
//					}
//				}
//			}
//		}
//// sending request to wallet service to reduce balance with JSON payload of the form {"custId": num, "amount": z}
//		WalletDelivery wd = new WalletDelivery(co.getCustID(), Integer.parseInt(cost));
//		String walletDeductUrl = "http://host.docker.internal:8082/deductBalance";
//		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		try {
//			ResponseEntity<String> walletDeductResponse = restTemplate.postForEntity(walletDeductUrl, wd, String.class);
//			if(walletDeductResponse.getStatusCodeValue() == 201)
//			{
//				deducted = true;
//			}
//		}
//		catch(HttpClientErrorException e)
//		{
//			return new ResponseEntity<OrderId>(HttpStatus.GONE);
//			
//		}
//
//		//Sending below json payload to restaurant to accept order
//		//JSON payload of the form {“restId”: num, “itemId”: x, “qty”: y}
//		if(deducted == true)
//		{
//			RestaurantOrder ro = new RestaurantOrder(co.getRestID(), co.getItemID(), co.getQuantity());
//			String acceptOrderUrl = "http://host.docker.internal:8080/acceptOrder";
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			try {
//				ResponseEntity<String> acceptOrderResponse = restTemplate.postForEntity(acceptOrderUrl, ro, String.class);
//				if(acceptOrderResponse.getStatusCodeValue() == 201)
//				{
////					orderAccepted = true;
//				}
//			}
//			//if order not accepted then adding the balance back 
//			catch(HttpClientErrorException e)
//			{
//				String walletAddUrl = "http://host.docker.internal:8082/addBalance";
//				headers.setContentType(MediaType.APPLICATION_JSON);
//				try {
//					ResponseEntity<String> walletAddResponse = restTemplate.postForEntity(walletAddUrl, wd, String.class);
//					if(walletAddResponse.getStatusCodeValue() == 201)
//					{
////						orderAccepted = true;
//					}
//				}
//				catch(HttpClientErrorException en){
//				}
//				
//				return new ResponseEntity<OrderId>(HttpStatus.GONE);
//			}
//			
//		}
//		//Accepting order and creating new entry in orderMap
//		OrderStatus newOrder = new OrderStatus(orderId, "unassigned", -1);
//		orderMap.put(String.valueOf(orderId), newOrder);
//		OrderId result = new OrderId(orderId);
//		//If agent is available then assign it to new order
//		for(Map.Entry<String, String> entry : agentMap.entrySet())
//		{
//			if(entry.getValue().equals("available"))
//			{
//				newOrder.setStatus("assigned");
//				newOrder.setAgentId(Integer.parseInt(entry.getKey()));
//				agentMap.put(entry.getKey(), "unavailable");
//				orderMap.put(String.valueOf(orderId), newOrder);
//				break;
//			}
//		}
//		orderId++;
//		return new ResponseEntity<OrderId>(result, HttpStatus.CREATED);
//	}

	@Override
	public ResponseEntity<OrderId> requestOrder(CustOrder co) 
	{
		int cost = 0;
		Integer restId = co.getRestID();
//		Integer custId = co.getCustID();
		Integer itemId = co.getItemID();
		int quantity = co.getQuantity();
		String dbUrl = "";
		boolean deducted = false;
		//Calculating the total billing amount of the order
//		dbUrl = "http://localhost:8080/getPrice";
		dbUrl = "http://abhidatabase:8080/getPrice";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Integer> parameter = new HashMap<>();
		parameter.put("restId", restId);
		parameter.put("itemId", itemId);
		HttpEntity<Map<String, Integer>> payload = new HttpEntity<>(parameter, headers);
		ResponseEntity<String> dbResponse = restTemplate.postForEntity(dbUrl, payload, String.class);
		cost = Integer.parseInt(dbResponse.getBody()) * quantity;
		
		
// sending request to wallet service to reduce balance with JSON payload of the form {"custId": num, "amount": z}
		WalletDelivery wd = new WalletDelivery(co.getCustID(), cost);
//		String walletDeductUrl = "http://host.docker.internal:8082/deductBalance";
//		String walletDeductUrl = "http://localhost:8082/deductBalance";
		String walletDeductUrl = "http://abhiwallet:8080/deductBalance";
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			ResponseEntity<String> walletDeductResponse = restTemplate.postForEntity(walletDeductUrl, wd, String.class);
			if(walletDeductResponse.getStatusCodeValue() == 201)
			{
				deducted = true;
			}
		}
		catch(HttpClientErrorException e)
		{
			return new ResponseEntity<OrderId>(HttpStatus.GONE);
			
		}

		//Sending below json payload to restaurant to accept order
		//JSON payload of the form {“restId”: num, “itemId”: x, “qty”: y}
		if(deducted == true)
		{
			RestaurantOrder ro = new RestaurantOrder(co.getRestID(), co.getItemID(), co.getQuantity());
//			String acceptOrderUrl = "http://host.docker.internal:8080/acceptOrder";
//			String acceptOrderUrl = "http://localhost:8080/acceptOrder";
			String acceptOrderUrl = "http://abhirestaurant:8080/acceptOrder";
			headers.setContentType(MediaType.APPLICATION_JSON);
			try {
				ResponseEntity<String> acceptOrderResponse = restTemplate.postForEntity(acceptOrderUrl, ro, String.class);
				if(acceptOrderResponse.getStatusCodeValue() == 201)
				{
//					orderAccepted = true;
				}
			}
			//if order not accepted then adding the balance back 
			catch(HttpClientErrorException e)
			{
//				String walletAddUrl = "http://host.docker.internal:8082/addBalance";
//				String walletAddUrl = "http://localhost:8082/addBalance";
				String walletAddUrl = "http://abhiwallet:8080/addBalance";
				headers.setContentType(MediaType.APPLICATION_JSON);
				try {
					ResponseEntity<String> walletAddResponse = restTemplate.postForEntity(walletAddUrl, wd, String.class);
					if(walletAddResponse.getStatusCodeValue() == 201)
					{
//						orderAccepted = true;
					}
				}
				catch(HttpClientErrorException en){
				}
				
				return new ResponseEntity<OrderId>(HttpStatus.GONE);
			}
			
		}
		//Accepting order and creating new entry in orderMap
		OrderStatus newOrder = new OrderStatus(orderId, "unassigned", -1);
//		dbUrl = "http://localhost:8080/getAgentByMinId";
//		restTemplate = new RestTemplate();
//		headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
////		HttpEntity<Map<String, Integer>> payload2 = new HttpEntity<>(newOrder, headers);
//		ResponseEntity<String> dbResponse2 = restTemplate.getForEntity(dbUrl, String.class);
////		int temp = Integer.parseInt(dbResponse2.getBody());
////		System.out.print(temp);
//		if(Integer.parseInt(dbResponse2.getBody()) != -1)
//		{
//			newOrder.setAgentId(Integer.parseInt(dbResponse2.getBody()));
//			newOrder.setStatus("assigned");
//			
//			dbUrl = "http://localhost:8080/setAgentStatus";
//			restTemplate = new RestTemplate();
//			headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
////			Map<String, Integer> parameter2 = new HashMap<>();
////			parameter2.put("agentId", temp);
//			HttpEntity<String> payload3 = new HttpEntity<>(String.valueOf(Integer.parseInt(dbResponse2.getBody())), headers);
//			ResponseEntity<String> dbResponse4 = restTemplate.postForEntity(dbUrl, payload3, String.class);
//		}
		
//		dbUrl = "http://localhost:8080/addOrder";
		dbUrl = "http://abhidatabase:8080/addOrder";
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<OrderStatus> payload2 = new HttpEntity<>(newOrder, headers);
		restTemplate.postForEntity(dbUrl, payload2, String.class);
		
		
//		dbUrl = "http://localhost:8080/getOrderAgentDetail";
		dbUrl = "http://abhidatabase:8080/getOrderAgentDetail";
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Integer> map = new HashMap<>();
		map.put("orderId", orderId);
		Integer agentId = 0;
		HttpEntity<Map<String, Integer>> payload3 = new HttpEntity<>(map, headers);
		ResponseEntity<String> dbResponse4 = restTemplate.postForEntity(dbUrl, payload3, String.class);
		agentId = Integer.parseInt(dbResponse4.getBody());
		
		
//		dbUrl = "http://localhost:8080/orderStatus";
		dbUrl = "http://abhidatabase:8080/orderStatus";
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Integer> map1 = new HashMap<>();
		map1.put("orderId", orderId);
		String status = null;
		HttpEntity<Map<String, Integer>> payload4 = new HttpEntity<>(map1, headers);
		ResponseEntity<String> dbResponse5 = restTemplate.postForEntity(dbUrl, payload4, String.class);
		status = dbResponse5.getBody();
		
		OrderStatus os = new OrderStatus();
		os.setOrderId(orderId);
		os.setStatus(status);
		os.setAgentId(agentId);
		
		
		
		
		orderMap.put(String.valueOf(orderId), newOrder);
		OrderId result = new OrderId(orderId);
		//If agent is available then assign it to new order
//		for(Map.Entry<String, String> entry : agentMap.entrySet())
//		{
//			if(entry.getValue().equals("available"))
//			{
//				newOrder.setStatus("assigned");
//				newOrder.setAgentId(Integer.parseInt(entry.getKey()));
//				agentMap.put(entry.getKey(), "unavailable");
//				orderMap.put(String.valueOf(orderId), newOrder);
//				break;
//			}
//		}
		
		
		orderId++;
		return new ResponseEntity<OrderId>(result, HttpStatus.CREATED);
	}

	
	
	
	//with JSON payload of the form {"orderId": num}, where num is an orderId
//	@Override
//	public ResponseEntity<String> orderDelivered(OrderId orderId) 
//	{
//		int agentId = 0;
//		for(Map.Entry<String, OrderStatus> entry : orderMap.entrySet())
//		{
//			if(Integer.parseInt(entry.getKey()) == orderId.getOrderId())
//			{
//				OrderStatus check = entry.getValue();
//				//If order unassigned it will not be delivered
//				if(check.getStatus().equals("unassigned"))
//				{
//					return new ResponseEntity<String>(HttpStatus.CREATED);
//				}
//				//Mark the status of orderId num as delivered 
//				//And mark the status of the agentId who was assigned this order as available.
//				else
//				{
//					check.setStatus("delivered");
//					orderMap.put(entry.getKey(), check);
//					agentMap.put(String.valueOf(check.getAgentId()), "available");
//					agentId = check.getAgentId();
//					break;
//				}
//				
//			}
//		}
//		//If any orderIds are currently in unassigned state, find the least numbered orderId y that is unassigned
//		//Mark x as unavailable again, mark y as assigned, and record that y is assigned to x.
//		for(Map.Entry<String, OrderStatus> entry : orderMap.entrySet())
//		{
//			OrderStatus check = entry.getValue();
//			if(check.getStatus().equals("unassigned"))
//			{
//				check.setStatus("assigned");
//				check.setAgentId(agentId);
//				orderMap.put(entry.getKey(), check);
//				agentMap.put(String.valueOf(agentId), "unavailable");
//				break;
//				
//			}
//		}
//		return new ResponseEntity<String>(HttpStatus.CREATED);
//	}

	//to get the order status response JSON of the form {"orderId": num, "status": x, “agentId”: y}, 
	//where x is unassigned, or assigned, or delivered.
//	@Override
//	public ResponseEntity<OrderStatus> getOrderStatus(String num) 
//	{
//		for(Map.Entry<String, OrderStatus> entry : orderMap.entrySet())
//		{
//			if(entry.getKey().equals(num))
//			{
//				return new ResponseEntity<OrderStatus>(entry.getValue(),HttpStatus.OK);
//			}
//		}
//		return new ResponseEntity<OrderStatus>(HttpStatus.NOT_FOUND);
//	}
	
	@Override
	public ResponseEntity<OrderStatus> getOrderStatus(String num) 
	{
		OrderStatus os = new OrderStatus();
//		String dbUrl = "http://localhost:8080/orderStatus";
		String dbUrl = "http://abhidatabase:8080/orderStatus";
//		String dbUrl = "http://host.docker.internal:8080/orderStatus";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Integer> parameter = new HashMap<>();
		parameter.put("orderId", Integer.parseInt(num));
		HttpEntity<Map<String, Integer>> he = new HttpEntity<>(parameter, headers);
		ResponseEntity<String> dbresponse = restTemplate.postForEntity(dbUrl, he, String.class);
		if(dbresponse.getBody() == null)
		{
			return new ResponseEntity<OrderStatus>(HttpStatus.NOT_FOUND);
		}
		os.setOrderId(Integer.parseInt(num));
		os.setStatus(dbresponse.getBody());
//		dbUrl = "http://localhost:8080/orderAgentId";
		dbUrl = "http://abhidatabase:8080/orderAgentId";
//		dbUrl = "http://host.docker.internal:8080/orderAgentId";
		Map<String, Integer> parameter2 = new HashMap<>();
		parameter2.put("orderId", Integer.parseInt(num));
		HttpEntity<Map<String, Integer>> he2 = new HttpEntity<>(parameter2, headers);
		try
		{
			ResponseEntity<String> dbresponse2 = restTemplate.postForEntity(dbUrl, he2, String.class);
			os.setAgentId(Integer.parseInt(dbresponse2.getBody()));
		}
		catch(HttpClientErrorException e)
		{
			return new ResponseEntity<OrderStatus>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<OrderStatus>(os, HttpStatus.OK);
		
	}

	//getting all the orders
	@Override
	public Map<String, OrderStatus> getAllOrder() 
	{
		return orderMap;
	}

	@Override
	public ResponseEntity<String> orderDelivered(OrderId orderId) 
	{
		int agentId = 0;
//		String dbUrl = "http://localhost:8080/orderStatus";
		String dbUrl = "http://abhidatabase:8080/orderStatus";
		Map<String, Integer> parameter = new HashMap<>();
		parameter.put("orderId", orderId.getOrderId());
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Integer>> payload = new HttpEntity<>(parameter, headers);
		ResponseEntity<String> dbresponse = restTemplate.postForEntity(dbUrl, payload, String.class);
		if(dbresponse.getBody().equals("unassigned"))
		{
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}
		else
		{
//			dbUrl = "http://localhost:8080/getOrderAgentDetail";
			dbUrl = "http://abhidatabase:8080/getOrderAgentDetail";
			Map<String, Integer> parameter1 = new HashMap<>();
			parameter1.put("orderId", orderId.getOrderId());
			restTemplate = new RestTemplate();
			headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Map<String, Integer>> payload1 = new HttpEntity<>(parameter1, headers);
			ResponseEntity<String> dbresponse1 = restTemplate.postForEntity(dbUrl, payload1, String.class);
			agentId = Integer.parseInt(dbresponse1.getBody());
			
//			dbUrl = "http://localhost:8080/setOrderStatus";
			dbUrl = "http://abhidatabase:8080/setOrderStatus";
			Map<String, Integer> parameter2 = new HashMap<>();
			parameter2.put("orderId", orderId.getOrderId());
			parameter2.put("agentId", agentId);
			restTemplate = new RestTemplate();
			headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Map<String, Integer>> payload2 = new HttpEntity<>(parameter2, headers);
			restTemplate.postForEntity(dbUrl, payload2, String.class);
			
		}
		
//		dbUrl = "http://localhost:8080/orderAssign";
		dbUrl = "http://abhidatabase:8080/orderAssign";
		Map<String, Integer> parameter3 = new HashMap<>();
		parameter3.put("agentId", agentId);
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Integer>> payload3 = new HttpEntity<>(parameter3, headers);
		restTemplate.postForEntity(dbUrl, payload3, String.class);
		
		
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
}
