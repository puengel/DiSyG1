package com.airport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


@NamedQuery(name="parkingspot.findAll", query="select a from Parkingspot a order by a.id")

@Entity
public class Parkingspot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String airplaneIdentifyer;
	private String arrivalTime;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAirplaneIdentifyer() {
		return airplaneIdentifyer;
	}

	public void setAirplaneIdentifyer(String name) {
		this.airplaneIdentifyer = name;
	} 
	
	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	} 
}
