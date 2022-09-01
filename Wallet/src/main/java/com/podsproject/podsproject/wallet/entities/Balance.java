package com.podsproject.podsproject.wallet.entities;

public class Balance 
{
	public int custId;
	public int balance;
	public Balance(int custId, int balance) {
		super();
		this.custId = custId;
		this.balance = balance;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "Balance [custId=" + custId + ", balance=" + balance + "]";
	}
	
}
