package com.airport.web.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.sun.media.sound.AiffFileReader;

@ManagedBean(name="airportBean")
@SessionScoped
public class AirportBean implements Serializable {
	private static final long serialVersionUID = 1665363412715858198L;

	@EJB
	private AirportEJB airportEJB;
	
	private Airplane airplane;
	
	public AirportBean() {
		System.out.println("AIRPORT: " + UUID.randomUUID());
	}
	
	@PostConstruct
	private void init() {
		airplane = new Airplane();		
		initRunways(4);
		initParkingspots(8);			
	}
	
	public void initParkingspots(int p) {
		for(int i = 0; i < p ;i++) {
			airportEJB.store(new Parkingspot()); 
		}
		
	}

	public void initRunways(int r) {
		for(int i = 0; i < 4 ;i++) {
			airportEJB.store(new Runway()); 
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
		return airportEJB.getRunways().get(id);
	}
	
	public Parkingspot getParkingspot(int id) {
		return airportEJB.getParkingspots().get(id);
	}
	
	public void store() {
//		airportEJB.store(airplane);
//		airplane = new Airplane();
		Iterator<Airplane> airplaneIterator = airportEJB.getAirplanes().iterator();
		boolean alreadyStored = false;
				
		// Search if the plane is already landing
		while(airplaneIterator.hasNext() && !alreadyStored) {
			Airplane a = airplaneIterator.next();

			if(a.getIdentifyer().equals(airplane.getIdentifyer())
				&& a.getAirline().equals(airplane.getAirline())) {
				alreadyStored = true;
			}
		}
		
		if (!alreadyStored){
			airportEJB.store(airplane);
			airplane = new Airplane();
		}
	}
	
	public void initiateLanding(Airplane airplane) {
		Iterator<Runway> runwayIterator = airportEJB.getRunways().iterator();
		Iterator<Parkingspot> parkingspotIterator = airportEJB.getParkingspots().iterator();
		boolean alreadyLanded = false;
		boolean alreadyParked = false;
		
		// Search if the plane is already landing
		while(parkingspotIterator.hasNext() && !alreadyParked) {
			Parkingspot p = parkingspotIterator.next();
			if(p.getAirplaneId() == airplane.getId()) {
				alreadyParked = true;
			}
		}
		
		// Search if the plane is already landing
		while(runwayIterator.hasNext() && !alreadyLanded && !alreadyParked) {
			Runway r = runwayIterator.next();
			if(r.getPlaneId() == airplane.getId()) {
				alreadyLanded = true;
			}
		}
		
		runwayIterator = airportEJB.getRunways().iterator();
		
		if(!alreadyLanded && !alreadyParked) {
			while(runwayIterator.hasNext()) {
				Runway r = runwayIterator.next();
				if(r.getInUse() == false) {
					airportEJB.update(r, true, airplane.getId());
					return;
				}
			}
		}
	}
	
	public void endLanding(Airplane airplane) {
		Iterator<Runway> runwayIterator = airportEJB.getRunways().iterator();
		boolean landing = false;
		
		while(runwayIterator.hasNext()) {
			Runway r = runwayIterator.next();
			if(r.getPlaneId() == airplane.getId()) {
				airportEJB.update(r, false, airplane.getId());
				landing = true;
			}
		}
		
		if(landing) {
			parkAirplane(airplane);
		}
	}
	
	public void parkAirplane(Airplane airplane) {
		Iterator<Parkingspot> parkingspotIterator = airportEJB.getParkingspots().iterator();
		String timeStamp = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss").format(Calendar.getInstance().getTime());
		boolean alreadyParked = false;
		
		// Search if the plane is already landing
		while(parkingspotIterator.hasNext() && !alreadyParked) {
			Parkingspot p = parkingspotIterator.next();
			if(p.getAirplaneId() == airplane.getId()) {	
				alreadyParked = true;
			}
		}
		
		parkingspotIterator = airportEJB.getParkingspots().iterator();
		
		if(!alreadyParked) {
			while(parkingspotIterator.hasNext()) {
				Parkingspot p = parkingspotIterator.next();
				if(p.getAirplaneIdentifyer() == null ) {
					airportEJB.park(p, airplane, timeStamp);
					break;
				}
			}
		}
		
	}
}
