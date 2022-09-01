package com.Delivery.Delivery.entities;

public class Agent {
	public int agentId;
	public String status; 
	
	
	public Agent(int agentId, String status) {
		super();
		this.agentId = agentId;
		this.status = status;
	}
	
	public Agent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getAgentId() {
		return agentId;
	}
	
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Agent [agentId=" + agentId + ", status=" + status + "]";
	}
	
	
	
	
}
