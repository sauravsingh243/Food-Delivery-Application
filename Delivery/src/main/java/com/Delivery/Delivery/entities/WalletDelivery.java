package com.Delivery.Delivery.entities;

public class WalletDelivery {
	public int custId;
	public int amount;
	public WalletDelivery(int custID, int amount) {
		super();
		this.custId = custID;
		this.amount = amount;
	}
	public int getCustID() {
		return custId;
	}
	public void setCustID(int custID) {
		this.custId = custID;
	}
	public int getBalance() {
		return amount;
	}
	public void setBalance(int amount) {
		this.amount = amount;
	}
	

}
