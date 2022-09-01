package com.Delivery.Delivery.entities;

public class CustOrder {
	public int custId;
	public int restId;
	public int itemId;
	public int qty;
	public CustOrder(int custId, int restId, int itemId, int qty) {
		super();
		this.custId = custId;
		this.restId = restId;
		this.itemId = itemId;
		this.qty = qty;
	}
	public int getCustID() {
		return custId;
	}
	public void setCustID(int custId) {
		this.custId = custId;
	}
	public int getRestID() {
		return restId;
	}
	public void setRestID(int restId) {
		this.restId = restId;
	}
	public int getItemID() {
		return itemId;
	}
	public void setItemID(int itemId) {
		this.itemId = itemId;
	}
	public int getQuantity() {
		return qty;
	}
	public void setQuantity(int qty) {
		this.qty = qty;
	}
	

}
