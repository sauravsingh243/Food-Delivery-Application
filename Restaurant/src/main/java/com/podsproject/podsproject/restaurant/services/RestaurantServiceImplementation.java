package com.podsproject.podsproject.restaurant.services;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.podsproject.podsproject.restaurant.entities.Inventory;
import com.podsproject.podsproject.restaurant.entities.Order;



@Service
public class RestaurantServiceImplementation implements RestaurantServices {
	
	Map<Integer, Map<Integer, Inventory>> restaurant;
	Map<Integer, Inventory> invent;
	
	// Accept order : JSON payload of the form {“restId”: num, “itemId”: x, “qty”: y}
	@Override
	public ResponseEntity<String> acceptOrder(Order order) 
	{
		
		for(Map.Entry<Integer, Map<Integer, Inventory>> entry : restaurant.entrySet())
		{
			if(entry.getKey() == order.getRestId())//If restuarant id matches
			{
				Map<Integer, Inventory> temp = entry.getValue();
				for(Map.Entry<Integer, Inventory> var : temp.entrySet())
				{
					//If item id matches then accept order and reduce quantity
					if(var.getKey() == order.getItemId())
					{
						Inventory var2 = var.getValue();
						if(var2.getQty() >= order.getQty())//If required quantity of item is available then reduce quantity
						{
							var2.setQty(var2.getQty() - order.getQty());//Reduce qunatity by order placed
							//return String.valueOf(cost);
							return new ResponseEntity<String>(HttpStatus.CREATED);
						}
						else
						{
							break;
						}
					}
				}
			}
		}
		return new ResponseEntity<String>(HttpStatus.GONE);//If inventory is less then return 410
	}
	
	//To initialize the restuarant entries from initialData.txt
	@Override
	public ResponseEntity<String> reInitialize() throws IOException 
	{
		FileInputStream f = null;
	    try 
	    {
	    	String path = new File("").getAbsolutePath();
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
	    restaurant = new HashMap<>();
	    String resID = null;
	    while ((Line = br.readLine()) != null)   
	    {
	    	Line = Line.strip();
	    	if (Line.charAt(0) == '*')
	    	{
	    		restaurant.put(Integer.parseInt(resID), invent);
	    		break;
	        }
	    	else
	    	{
		    	String[] var = Line.split(" ");
		    	if(var.length == 2)
		    	{
		    		if(resID == null)
		    		{
		    			resID = var[0];
			    		invent = new HashMap<>();
		    		}
		    		else
		    		{
		    			restaurant.put(Integer.parseInt(resID), invent);
			    		resID = var[0];
			    		invent = new HashMap<>();
		    		}
		    		
		    	}
		    	else
		    	{
		    		Inventory invt = new Inventory(Integer.parseInt(var[0]), Integer.parseInt(var[1]), Integer.parseInt(var[2]));
		    		invent.put(Integer.parseInt(var[0]), invt);
		    	}
	    	}
	    	
	    }
	    f.close();
	    

		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	//for getting the inventory remaining 
	@Override
	public Map<Integer, Map<Integer, Inventory>> getInventory(Integer restID) 
	{
		for(Map.Entry<Integer, Map<Integer, Inventory>> entry : restaurant.entrySet())
		{
			if(entry.getKey() == restID)
			{
				return restaurant;
			}
		}
		
		return null;
	}
	
	//Function to refill the inventory 
	//JSON payload of the form {“restId”: num, “itemId”: x, “qty”: y}
	@Override
	public ResponseEntity<String> refillInventory(Order refill) 
	{
		
		for(Map.Entry<Integer, Map<Integer, Inventory>> entry : restaurant.entrySet())
		{
			if(entry.getKey() == refill.getRestId())//Maching for which restuarnt we need to refill
			{
				Map<Integer, Inventory> temp = entry.getValue();
				for(Map.Entry<Integer, Inventory> var : temp.entrySet())
				{
					if(var.getKey() == refill.getItemId())//matching itemid
					{
						Inventory var2 = var.getValue();
						var2.setQty(var2.getQty() + refill.getQty());//Increasing the item quantity 
						
					}
				}
			}
		}
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	
	

}
