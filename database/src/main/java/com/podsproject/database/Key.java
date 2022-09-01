package com.podsproject.database;

import java.io.Serializable;
import java.util.Objects;

public class Key implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Integer restId;
	
	public Integer itemId;

	public Key() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Key(Integer restId, Integer itemId) {
		super();
		this.restId = restId;
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return "Key [restId=" + restId + ", itemId=" + itemId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId, restId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key other = (Key) obj;
		return Objects.equals(itemId, other.itemId) && Objects.equals(restId, other.restId);
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
	
	

}
