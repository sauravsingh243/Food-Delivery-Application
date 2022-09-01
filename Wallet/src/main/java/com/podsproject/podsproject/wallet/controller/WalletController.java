package com.podsproject.podsproject.wallet.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.podsproject.podsproject.wallet.entities.Balance;
import com.podsproject.podsproject.wallet.entities.Wallet;
import com.podsproject.podsproject.wallet.services.WalletServices;

@RestController
public class WalletController 
{
	@Autowired
	public WalletServices ws;
	private final Object obj = new Object();

	// where custID is a custId. Return HTTP status code 200, and response JSON of the form {“custId”: custID, “balance”: z}, where z is the current balance of custId num.
	@GetMapping("/balance/{custID}")
	public ResponseEntity<Balance> getBalance(@PathVariable String custID, HttpServletResponse response)
	{
		synchronized (obj) 
		{
			return this.ws.getBalance(Integer.parseInt(custID));
		}	
		
	}	
	
	// It will be invoked by delivery service. JSON payload of the form {"custId": num, "amount": z} is passed.
	// Increase the balance of custId num by z, and return HTTP status code 201.
	@PostMapping("/addBalance")
	public ResponseEntity<String> addBalance(@RequestBody Wallet w)
	{
		synchronized (obj) 
		{
			return this.ws.addBalance(w);
		}
		
	}
	
	
	// It will be invoked by delivery service. JSON payload of the form {"custId": num, "amount": z} is passed.
	// If current balance of custId num is less than z, return HTTP status code 410, else reduce custId num's balance by z and return HTTP status code 201.
	@PostMapping("/deductBalance")
	public ResponseEntity<String> deductBalance(@RequestBody Wallet w)
	{
		synchronized (obj) 
		{
			return this.ws.deductBalance(w);
		}
		
	}
	
	
	// Set balance of all customers to the initial value as given in the /initialData.txt file. Return HTTP status code 201.
	@PostMapping("/reInitialize")
	public ResponseEntity<String> reInitialize() throws IOException
	{
		synchronized (obj) 
		{
			return this.ws.reInitialize();
		}
		
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public ResponseEntity<String> reInitialize1() throws IOException
	{
		synchronized (obj) 
		{
			return this.ws.reInitialize();
		}
		
	}
	

}
