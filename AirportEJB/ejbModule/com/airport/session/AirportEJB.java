package com.airport.session;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.airport.model.Airplane;
import com.airport.model.Parkingspot;
import com.airport.model.Runway;

@Stateless
public class AirportEJB {

	@PersistenceContext(unitName="airport")
	private EntityManager entityManager;
	
	
	
	public void store(Airplane airplane) {
		entityManager.persist(airplane);
	}
	
	public void store(Runway runway) {
		entityManager.persist(runway);
	}
	
	public void store(Parkingspot parkingspot) {
		entityManager.persist(parkingspot);
	}
	
	public List<Airplane> getAirplanes() {
		Query query = entityManager.createNamedQuery("airplane.findAll");
		
		@SuppressWarnings("unchecked")
		List<Airplane> airplanes = query.getResultList();
		return airplanes;
	}
	
	public List<Runway> getRunways() {
		Query query = entityManager.createNamedQuery("runway.findAll");
		
		@SuppressWarnings("unchecked")
		List<Runway> runways = query.getResultList();
		return runways;
	}
	
	public List<Parkingspot> getParkingspots() {
		Query query = entityManager.createNamedQuery("parkingspot.findAll");
		
		@SuppressWarnings("unchecked")
		List<Parkingspot> parkingspots = query.getResultList();
		return parkingspots;
	}
	
	public void update(Runway runway, boolean change, int airplaneID) {
		
		runway.setInUse(change, airplaneID);
		
		entityManager.merge(runway);
	}
	
	public void update(Airplane airplane) {
		entityManager.merge(airplane);
	}

	public void park(Parkingspot p, Airplane airplane, String timeStamp) {
		p.setAirplaneIdentifyer(airplane.getIdentifyer());
		p.setAirline(airplane.getAirline());
		p.setAirplaneId(airplane.getId());
		p.setArrivalTime(timeStamp);
		entityManager.merge(p);
	}
	
	
}
