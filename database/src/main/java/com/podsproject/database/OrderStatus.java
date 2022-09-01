package com.podsproject.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OrderStatusTable")
public class OrderStatus 
{
	@Id
	public Integer orderId;
	
	@Column(name = "status", nullable = false)
	public String status;
	
	@Column(name = "agentId", nullable = false)
	public Integer agentId;

	public OrderStatus(Integer orderId, String status, Integer agentId) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.agentId = agentId;
	}

	@Override
	public String toString() {
		return "OrderStatus [orderId=" + orderId + ", status=" + status + ", agentId=" + agentId + "]";
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public OrderStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
	
	
	

}
