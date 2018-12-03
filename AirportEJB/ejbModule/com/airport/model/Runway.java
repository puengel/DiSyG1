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
	private int planeID;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean getInUse() {
		return inUse;
	}
	
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	
	public int getPlaneId() {
		return planeID;
	}

	public void setPlaneId(int planeID) {
		this.planeID = planeID;
	}
}