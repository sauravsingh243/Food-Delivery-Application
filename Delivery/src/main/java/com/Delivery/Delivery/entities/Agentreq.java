package com.Delivery.Delivery.entities;

public class Agentreq {
	public int agentId;

	public Agentreq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Agentreq(int agentId) {
		super();
		this.agentId = agentId;
	}

	@Override
	public String toString() {
		return "Agentreq [agentId=" + agentId + "]";
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	
}
