package pl.toms.plane.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.toms.plane.entity.Flight;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Integer>{
	
	Flight findByFlightNumber(String number);
	
	Flight findByDepartureAirport(String departureAirport);
	
	Flight findByArrivalAirport(String departureAirport);
	
	Flight findOneById(Integer id);
}
