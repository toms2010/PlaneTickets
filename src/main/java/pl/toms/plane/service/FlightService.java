package pl.toms.plane.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.toms.plane.entity.Flight;
import pl.toms.plane.entity.Plane;
import pl.toms.plane.entity.Seat;
import pl.toms.plane.repository.FlightRepository;
import pl.toms.plane.repository.SeatRepository;

@Service
public class FlightService {
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	public List<Flight> getFlights() {
		return (List<Flight>) flightRepository.findAll();
	}

	public Flight getFlight(Integer id) {
//		Optional<Flight> flight = flightRepository.findById(id);
		Flight flight = flightRepository.findOneById(id);
		System.out.println(flight);
		return flight;  //TODO
	}

	public Flight addFlight(Flight flight) {
		flight = flightRepository.save(flight);
		buildFlightSeats(flight);
		return flight;
	}

	private void buildFlightSeats(Flight flight) {
		Plane plane = flight.getPlane();
		int seatsRows = plane.getSeatsRows();
		int seatsInRow = plane.getSeatsInRow();
		int seatNumber=0;
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

	public Flight updateFlight(Flight flight) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteFlight(int flightId) {
		// TODO Auto-generated method stub
		
	}
}
