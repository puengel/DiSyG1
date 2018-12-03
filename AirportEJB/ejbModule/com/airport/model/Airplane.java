package com.airport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
@NamedQuery(name="airplane.findAll", query="select a from Airplane a order by a.identifyer")

@Entity
public class Airplane {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String identifyer;
	private String airline;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdentifyer() {
		return identifyer;
	}

	public void setIdentifyer(String identifyer) {
		this.identifyer = identifyer;
	} 
	
	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	} 
}
