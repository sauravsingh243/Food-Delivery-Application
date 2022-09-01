package com.podsproject.podsproject.wallet.services;

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

import com.podsproject.podsproject.wallet.entities.Balance;
import com.podsproject.podsproject.wallet.entities.Wallet;


@Service
public class WalletServiceImplementation implements WalletServices {

	Map<Integer, Integer> balance;
	
	

// where custID is a custId. Return HTTP status code 200, and response JSON of the form {“custId”: custID, “balance”: z}, where z is the current balance of custId num
	@Override
	public ResponseEntity<Balance> getBalance(Integer custID) {
		
		Balance result = new Balance(0, 0);
		for (Map.Entry<Integer,Integer> entry : balance.entrySet())
		{
			int var = entry.getKey();
			if(var == custID)
			{
				result.setCustId(entry.getKey());
				result.setBalance(entry.getValue());
				break;
			}
		}
		return new ResponseEntity<Balance>(result, HttpStatus.OK);
	}

	// It will be invoked by delivery service. JSON payload of the form {"custId": num, "amount": z} is passed.
	// Increase the balance of custId num by z, and return HTTP status code 201.
	@Override
	public ResponseEntity<String> addBalance(Wallet w) {
		for (Map.Entry<Integer,Integer> entry : balance.entrySet())
		{
			if(entry.getKey() == w.getCustId())
			{
				balance.put(entry.getKey(), entry.getValue() + w.getAmount());
				break;
			}
		}
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	// It will be invoked by delivery service. JSON payload of the form {"custId": num, "amount": z} is passed.
		// If current balance of custId num is less than z, return HTTP status code 410, else reduce custId num's balance by z and return HTTP status code 201.
	@Override
	public ResponseEntity<String> deductBalance(Wallet w) 
	{
		for (Map.Entry<Integer,Integer> entry : balance.entrySet())
		{
			if(entry.getKey() == w.getCustId())
			{
				if(entry.getValue() >= w.getAmount())
				{
					balance.put(entry.getKey(), entry.getValue() - w.getAmount());
					return new ResponseEntity<String>(HttpStatus.CREATED);
				}
				else
				{
					break;
				}
			}
		}
		return new ResponseEntity<String>(HttpStatus.GONE);
	}
	
	
	
	

	// Set balance of all customers to the initial value as given in the /initialData.txt file. Return HTTP status code 201.	
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
	    balance = new HashMap<>();
	    while ((Line = br.readLine()) != null)   
	    {
	    	Line = Line.strip();
	    	if (Line.charAt(0) == '*')
	    	{
	    		break;
	        }
	    }
	    while ((Line = br.readLine()) != null)   
	    {
	    	Line = Line.strip();
	    	if (Line.charAt(0) == '*')
	    	{
	    		break;
	    	}
	    }
	    while ((Line = br.readLine()) != null)   
	    {
	    	Line = Line.strip();
	    	if (Line.charAt(0) == '*')
	    	{
	    		break;
	    	}
	    	balance.put(Integer.parseInt(Line), 0);
	    }
	    Line = br.readLine();
	    Line = Line.strip();
	    for (Map.Entry<Integer,Integer> entry : balance.entrySet())
	    	balance.put(entry.getKey(), Integer.parseInt(Line));
	    f.close();

	    return new ResponseEntity<String>(HttpStatus.CREATED);
	}
}
