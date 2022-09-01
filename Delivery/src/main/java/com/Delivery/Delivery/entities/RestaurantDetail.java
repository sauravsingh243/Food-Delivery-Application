package com.Delivery.Delivery.entities;



public class RestaurantDetail 
{
	public Integer restId;
	
	public Integer itemId;
	
	public Integer price;

	public RestaurantDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RestaurantDetail(Integer restId, Integer itemId, Integer price) {
		super();
		this.restId = restId;
		this.itemId = itemId;
		this.price = price;
	}

	@Override
	public String toString() {
		return "RestaurantDetail [restId=" + restId + ", itemId=" + itemId + ", price=" + price + "]";
	}

	public Integer getRestId() {
		return restId;
	}

	public void setRestId(Integer restId) {
		this.restId = restId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	
}
