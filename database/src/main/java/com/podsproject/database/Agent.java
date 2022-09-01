package com.podsproject.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Agent {

	@Id
	public Integer agentId;
	@Column(name = "status")
	public String status;
	@Override
	public String toString() {
		return "Agent [agentId=" + agentId + ", status=" + status + "]";
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Agent(Integer agentId, String status) {
		this.agentId = agentId;
		this.status = status;
	}
	public Agent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
