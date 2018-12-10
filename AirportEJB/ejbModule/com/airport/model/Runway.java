package com.airport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name="runway.findAll", query="select r from Runway r order by r.id")

@Entity
public class Runway {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private boolean inUse;
	
	private int airplaneID;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean getInUse() {
		return inUse;
	}
	
	public void setInUse(boolean inUse, int airplaneID) {
		this.inUse = inUse;
		if (inUse == true){
			this.airplaneID = airplaneID;
		} else {
			this.airplaneID = -1;		
		}
	}
	
	public int getPlaneId() {
		return airplaneID;
	}

	public void setAirplaneId(int airplaneID) {
		this.airplaneID = airplaneID;
	}
}