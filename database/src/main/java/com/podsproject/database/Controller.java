package com.podsproject.database;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller 
{
	
	public final AgentRepository agentRepository;
	public final OrderStatusRepository orderStatusRepository;
	public final RestaurantDetailRepository restaurantDetailRepository;
	private final Object obj = new Object();
	
	@Autowired
	public Controller(AgentRepository agentRepository, OrderStatusRepository orderStatusRepository, RestaurantDetailRepository restaurantDetailRepository)
	{
		this.agentRepository = agentRepository;
		this.orderStatusRepository = orderStatusRepository;
		this.restaurantDetailRepository = restaurantDetailRepository;
	}
	
	// It handles the request of agent sign in which is initiated by delivery controller Implementation
	@PostMapping("/agentSignIn")
	public void agentSignIn(@RequestBody Map<String, Integer> map)
	{
		synchronized (obj) 
		{
			Agent ag = new Agent();
			ag = agentRepository.findById(map.get("agentId"));
			if(ag.getStatus().equals("signed-out"))
			{
				List<OrderStatus> ls = orderStatusRepository.findByStatus("unassigned");
				if(ls.isEmpty() == false)
				{
					OrderStatus os = new OrderStatus();
					os = orderStatusRepository.findFirstByStatusOrderByOrderIdAsc("unassigned");
					os.setStatus("assigned");
					os.setAgentId(ag.getAgentId());
					orderStatusRepository.save(os);
					ag.setStatus("unavailable");
					agentRepository.save(ag);
					return;
				}
				ag.setStatus("available");
				agentRepository.save(ag);
				return;
			}
		}
		
	}
	
	// It handles the request of agent sign out which is initiated by delivery controller Implementation
	@PostMapping("/agentSignOut")
	public void agentSignOut(@RequestBody Map<String, Integer> map)
	{
		synchronized (obj) 
		{
			Agent ag = new Agent();
			ag = agentRepository.findById(map.get("agentId"));
			if(ag.getStatus().equals("available"))
			{
				ag.setStatus("signed-out");
				agentRepository.save(ag);
			}
		}
		
	}
	// It handles the request of order status which is initiated by delivery controller Implementation and it 
	// returns the status of the order
	@PostMapping("/orderStatus")
	public String getOrderStatus(@RequestBody Map<String, Integer> map)
	{
		synchronized (obj) 
		{
			OrderStatus os = new OrderStatus();
			os = orderStatusRepository.findById(map.get("orderId"));
			if(os == null)
			{
				return null;
			}
			return os.getStatus();
		}
		
	}

	// It handles the request of order agent Id which is initiated by delivery controller Implementation and it 
		// returns the agentId of the order
	@PostMapping("/orderAgentId")
	public Integer getOrderAgentId(@RequestBody Map<String, Integer> map)
	{
		synchronized (obj) 
		{
			OrderStatus os = new OrderStatus();
			os = orderStatusRepository.findById(map.get("orderId"));
			return os.getAgentId();
		}
		
	}
	
	// It handles the request of agent details which is initiated by delivery controller Implementation and it 
		// returns the status of the agentId
	@PostMapping("/agentDetail")
	public String getAgentDetail(@RequestBody Map<String, Integer> map)
	{
		synchronized (obj) 
		{
			Agent ag = new Agent();
			ag = agentRepository.findById(map.get("agentId"));
			return ag.getStatus();
		}
		
	}
	
	// It handles the request of order delete which is initiated by delivery controller Implementation and it deletes all the placed order
	@GetMapping("/removeAllOrder")
	public void removeAllOrder()
	{
		synchronized (obj) 
		{
			orderStatusRepository.deleteAll();
		}
		
	}
	
	// It handles the request of agent delete which is initiated by delivery controller Implementation and it deletes all the agents
	@GetMapping("/removeAllAgent")
	public void removeAllAgent()
	{
		synchronized (obj) 
		{
			agentRepository.deleteAll();
		}
		
	}
	
	// It handles the request of add agent which is initiated by delivery controller Implementation
	@PostMapping("/addAgent")
	public void addAgent(@RequestBody Agent ag)
	{
		synchronized (obj) 
		{
			agentRepository.save(ag);
		}
		
	}
	@GetMapping("/1")
	public String fn()
	{
		synchronized (obj) 
		{
			return "hey";
		}
		
	}
	
	// it returns the detail of all agents 
	@GetMapping("/getAllAgent")
	public List<Agent> getAllAgent()
	{
		synchronized (obj) 
		{
			return agentRepository.findAll();
		}
		
	}
	
	// It adds the restaurant in restaurant detail table, delivery controller calls it during initialization
	@PostMapping("/addRestaurant")
	public void addRest(@RequestBody RestaurantDetail rd)
	{
		synchronized (obj) 
		{
			restaurantDetailRepository.save(rd);
		}
		
	}
	
	// it returns the deatil of all restaurants
	@GetMapping("/allRest")
	public List<RestaurantDetail> getAll()
	{
		synchronized (obj) 
		{
			return restaurantDetailRepository.findAll();
		}
		
	}
	
	// It returns the price of given ItemId, delivery controller call it during request order
	@PostMapping("/getPrice")
	public Integer getPrice(@RequestBody Map<String, Integer> map)
	{
		synchronized (obj) 
		{
			List<RestaurantDetail> l;
			l = restaurantDetailRepository.findByRestIdAndItemId(map.get("restId"), map.get("itemId"));
			for(RestaurantDetail rd : l)
			{
				return rd.price;
			}
			return 0;
		}
		
	}
	
	// It adds the placed order to the order table
	@PostMapping("/addOrder")
	public void addOrder(@RequestBody OrderStatus os)
	{
		synchronized (obj) 
		{
			List<Agent> ls;
			ls = agentRepository.findByStatus("available");
			if(ls.isEmpty() == false)
			{
				os.setStatus("assigned");
				Agent ag = new Agent();
				ag = agentRepository.findFirstByStatusOrderByAgentIdAsc("available");
				ag.setStatus("unavailable");
				os.setAgentId(ag.getAgentId());
				agentRepository.save(ag);
				orderStatusRepository.save(os);
				
				
			}
			orderStatusRepository.save(os);
			return;
		}
		
	}
	
	// it returns the agent detail of given agentId
	@GetMapping("/getAgentByMinId")
	public Integer getAgentByMinId()
	{
		synchronized (obj) 
		{
			Agent ag = agentRepository.findFirstByStatusOrderByAgentIdAsc("available");
			if(ag == null)
			{
				return -1;
			}
			return ag.getAgentId();
		}
		
	}
	
//	@PostMapping("/setAgentStatus")
//	public void setAgentStatus(@RequestBody Map<String, Integer> map)
//	{
//		Agent ag = new Agent();
//		ag = agentRepository.findById(map.get("agentId"));
//		ag.setStatus("unavailable");
//		agentRepository.save(ag);
//	}
	
	// it sets the status of given agentId
	@PostMapping("/setAgentStatus")
	public void setAgentStatus(@RequestBody String agentId)
	{
		synchronized (obj) 
		{
			Agent ag = new Agent();
			ag = agentRepository.findById(Integer.parseInt(agentId));
			ag.setStatus("unavailable");
			System.out.print(ag.getAgentId());
			agentRepository.save(ag);
		}
		
	}
	
	// it returns the list of all orders
	@GetMapping("/allOrder")
	public List<OrderStatus> getAllOrder()
	{
		synchronized (obj) 
		{
			return orderStatusRepository.findAll();
		}
		
	}
	
	// it sets the status of given orderId
	@PostMapping("/setOrderStatus")
	public void setOrderStatus(@RequestBody Map<String, Integer> map)
	{
		synchronized (obj) 
		{
			OrderStatus os = new OrderStatus();
			os = orderStatusRepository.findById(map.get("orderId"));
			os.setStatus("delivered");
			orderStatusRepository.save(os);
			
			Agent ag = new Agent();
			ag = agentRepository.findById(map.get("agentId"));
			ag.setStatus("available");
			agentRepository.save(ag);
		}
		
		
	}
	
	// it reurns the agentId to which given orderId is assigned
	@PostMapping("/getOrderAgentDetail")
	public Integer getOrderDetail(@RequestBody Map<String, Integer> map)
	{
		synchronized (obj) 
		{
			OrderStatus os = new OrderStatus();
			os = orderStatusRepository.findById(map.get("orderId"));
			return os.getAgentId();
		}
		
	}
	
	// it assigns the given order to given agentId
	@PostMapping("/orderAssign")
	public void orderAssign(@RequestBody Map<String, Integer> map)
	{
		synchronized (obj) 
		{
			Agent ag = new Agent();
			ag = agentRepository.findById(map.get("agentId"));
			List<OrderStatus> ls = orderStatusRepository.findByStatus("unassigned");
			if(ls.isEmpty() == false)
			{
				OrderStatus os = new OrderStatus();
				os = orderStatusRepository.findFirstByStatusOrderByOrderIdAsc("unassigned");
				os.setStatus("assigned");
				os.setAgentId(ag.getAgentId());
				orderStatusRepository.save(os);
				ag.setStatus("unavailable");
				agentRepository.save(ag);
			}
			return;
		}
		
	}
}


