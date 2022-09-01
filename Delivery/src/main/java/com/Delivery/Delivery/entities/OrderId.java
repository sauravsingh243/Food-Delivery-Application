package com.Delivery.Delivery.entities;

public class OrderId {
	public int orderId;

	public OrderId(int orderId) {
		super();
		this.orderId = orderId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public OrderId() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "OrderId [orderId=" + orderId + "]";
	}
	

}
