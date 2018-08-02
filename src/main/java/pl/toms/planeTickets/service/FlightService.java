package pl.toms.planeTickets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.toms.planeTickets.entity.Flight;
import pl.toms.planeTickets.entity.Plane;
import pl.toms.planeTickets.entity.Seat;
import pl.toms.planeTickets.exception.NotFoundException;
import pl.toms.planeTickets.repository.FlightRepository;
import pl.toms.planeTickets.repository.SeatRepository;

@Service
public class FlightService {
	//TODO sprawdzenie czy nie null 
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	public List<Flight> getAllFlights() {
		return (List<Flight>) flightRepository.findAll();
	}

	public Flight getFlight(Integer id) {
//		Optional<Flight> flight = flightRepository.findById(id);
		Flight flight = flightRepository.findOneById(id);
		System.out.println(flight);
		return flight;  //TODO
	}

	public Flight addFlight(Flight flight) {
	    buildFlightSeats(flight);
		flight = flightRepository.save(flight);
		return flight;
	}
	
	public Flight updateFlight(Flight flight) {
		
		return flightRepository.save(flight);
	}

	public void deleteFlight(int flightId) {
		if (flightRepository.findOneById(flightId) == null)
			throw new NotFoundException("There is no flights with id: " + flightId);
		
		flightRepository.deleteById(flightId);
	}

	private void buildFlightSeats(Flight flight) {
		Plane plane = flight.getPlane();
		int seatsRows = plane.getSeatsRows();
		int seatsInRow = plane.getSeatsInRow();
		int seatNumber = 0;
		for (int i=0; i<seatsInRow; i++) {
			for(int j=0; j<seatsRows; j++) {
				Seat seat = new Seat();
				seat.setFlight(flight);
				seat.setNumber(seatNumber);
				seatNumber++;
				seat.setStatus(Seat.SeatStatus.F.getStatus());
				seatRepository.save(seat);
			}
		}
	}


}
