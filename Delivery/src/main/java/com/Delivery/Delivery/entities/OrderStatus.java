package com.Delivery.Delivery.entities;

public class OrderStatus {

	public OrderStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int orderId;
	public String status;
	public int agentId;
	public OrderStatus(int orderId, String status, int agentId) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.agentId = agentId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAgentId() {
		return agentId;
	}
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	
	
}
