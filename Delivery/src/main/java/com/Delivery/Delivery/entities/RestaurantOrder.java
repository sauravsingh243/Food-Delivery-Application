package com.Delivery.Delivery.entities;

public class RestaurantOrder {

	public int restId;
	public int itemId;
	public int qty;
	public RestaurantOrder(int restId, int itemId, int qty) {
		super();
		this.restId = restId;
		this.itemId = itemId;
		this.qty = qty;
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
