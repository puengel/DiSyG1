package com.airport.web.bean;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.airport.model.Airplane;
import com.airport.model.Runway;
import com.airport.session.AirportEJB;

@ManagedBean(name="airportBean")
@SessionScoped
public class AirportBean implements Serializable {
	private static final long serialVersionUID = 1665363412715858198L;

	@EJB
	private AirportEJB airportEJB;
	
	private Airplane airplane;
	
	private Runway runway1;
	private Runway runway2;
	private Runway runway3;
	private Runway runway4;
	
	public AirportBean() {
		System.out.println("AIRPORT: " + UUID.randomUUID());
	}
	
	@PostConstruct
	private void init() {
		airplane = new Airplane();
		runway1 = new Runway();
		runway2 = new Runway();
		runway3 = new Runway();
		runway4 = new Runway();
		runway1.setId(1);
		runway2.setId(2);
		runway3.setId(3);
		runway4.setId(4);
		
		System.out.println("runways initialized");
	}
	
	public List<Airplane> getAirplanes() {
		return airportEJB.getAirplanes();
	}
	
	public List<Runway> getRunways() {
		return airportEJB.getRunways();
	}
	
	public Airplane getAirplane() {
		return airplane;
	}
	
	public Runway getRunway(int id) {
		switch(id) {
		case 1: 
			return runway1;
			
		case 2: 
			return runway2;
			
		case 3: 
			return runway3;
			
		case 4: 
			return runway4;
			
		default: 
			System.out.println("This id does not exist");
			return null;
		}
		
	}
	
	public void store() {
		airportEJB.store(airplane);
		airplane = new Airplane();
	}
}
