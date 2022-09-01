package com.podsproject.podsproject.wallet.entities;

public class Wallet {
	public int custId;
	public int amount;
	public Wallet(int custId, int amount) {
		super();
		this.custId = custId;
		this.amount = amount;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	

}