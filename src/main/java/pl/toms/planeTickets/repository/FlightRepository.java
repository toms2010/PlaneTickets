package pl.toms.planeTickets.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import pl.toms.planeTickets.entity.Flight;

@Repository
public interface FlightRepository extends BaseRepository<Flight, Integer> {

    Flight findOneByFlightNumber(String number);

    List<Flight> findByDepartureAirport(String departureAirport);

    List<Flight> findByArrivalAirport(String departureAirport);
}
