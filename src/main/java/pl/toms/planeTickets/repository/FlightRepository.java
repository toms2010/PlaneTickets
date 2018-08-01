package pl.toms.planeTickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.toms.planeTickets.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer>{
	
	Flight findByFlightNumber(String number);
	
	Flight findByDepartureAirport(String departureAirport);
	
	Flight findByArrivalAirport(String departureAirport);
	
	Flight findOneById(Integer id);
}
