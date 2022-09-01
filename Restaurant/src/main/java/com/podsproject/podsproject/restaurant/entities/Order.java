package com.podsproject.podsproject.restaurant.entities;

public class Order {
	public int restId;
	public int itemId;
	public int qty;
	public Order(int restId, int itemId, int qty) {
		super();
		this.restId = restId;
		this.itemId = itemId;
		this.qty = qty;
	}
	public int getRestId() {
		return restId;
	}
	public void setRestId(int restId) {
		this.restId = restId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	

}
