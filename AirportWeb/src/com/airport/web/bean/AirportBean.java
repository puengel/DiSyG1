package com.airport.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.airport.model.Airplane;
import com.airport.model.Parkingspot;
import com.airport.model.Runway;
import com.airport.session.AirportEJB;

@ManagedBean(name="airportBean")
@SessionScoped
public class AirportBean implements Serializable {
	private static final long serialVersionUID = 1665363412715858198L;

	@EJB
	private AirportEJB airportEJB;
	
	private Airplane airplane;
	
	private List<Runway> runwayList = new ArrayList<Runway>() ;

	private List<Parkingspot> parkingspotList = new ArrayList<Parkingspot>() ;
	
	public AirportBean() {
		System.out.println("AIRPORT: " + UUID.randomUUID());
	}
	
	@PostConstruct
	private void init() {
		airplane = new Airplane();
		//adding 4 runways
		for(int i = 0; i < 4 ;i++) {
			runwayList.add(new Runway());
			airportEJB.store(runwayList.get(i)); 
		}
		
		for(int i = 0; i < 8 ;i++) {
			parkingspotList.add(new Parkingspot());
			airportEJB.store(parkingspotList.get(i)); 
		}
			
	}
	
	public List<Airplane> getAirplanes() {
		return airportEJB.getAirplanes();
	}
	
	public List<Runway> getRunways() {
		return airportEJB.getRunways();
	}
	
	public List<Parkingspot> getParkingspots() {
		return airportEJB.getParkingspots();
	}
	
	public Airplane getAirplane() {
		return airplane;
	}
	
	public Runway getRunway(int id) {
		return runwayList.get(id);
	}
	
	public Parkingspot getParkingspor(int id) {
		return parkingspotList.get(id);
	}
	
	public void store() {
		airportEJB.store(airplane);
		airplane = new Airplane();
	}
	
	public void initiateLanding() {
		Iterator<Runway> runwayIterator = runwayList.iterator();
		
		while(runwayIterator.hasNext()) {
			Runway r = runwayIterator.next();
			if(r.getInUse() == false) {
				airportEJB.update(r);
				break;
			}
		}
	}
	
	public void endLanding() {
		Iterator<Runway> runwayIterator = runwayList.iterator();
		
		while(runwayIterator.hasNext()) {
			Runway r = runwayIterator.next();
			if(r.getInUse() == true) {
				airportEJB.update(r);
			}
		}
	}
	
	public void parkAirplane(String airplaneName) {
		Iterator<Parkingspot> parkingspotIterator = parkingspotList.iterator();
		
		while(parkingspotIterator.hasNext()) {
			Parkingspot p = parkingspotIterator.next();
			if(p.getAirplaneName() == null ) {
				airportEJB.park(p, airplaneName);
				break;
			}
		}
		
	}
}
