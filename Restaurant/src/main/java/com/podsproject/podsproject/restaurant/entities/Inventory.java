package com.podsproject.podsproject.restaurant.entities;

public class Inventory {
	public int itemId;
	public int cost;
	public int qty;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public Inventory(int itemId, int cost, int qty) {
		super();
		this.itemId = itemId;
		this.cost = cost;
		this.qty = qty;
	}
	
	

}
