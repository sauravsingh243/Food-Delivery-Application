package com.podsproject.database;

import java.util.List;

import org.springframework.data.repository.Repository;


public interface AgentRepository extends Repository<Agent, Integer>
{
	
	Agent findById(Integer agentId);
	
	void save(Agent agentDetails);
	
	void deleteAll();
	
	Agent findFirstByStatusOrderByAgentIdAsc(String status);
	
	List<Agent> findByStatus(String status);

	List<Agent> findAll();

}
