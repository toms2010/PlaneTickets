package pl.toms.planeTickets.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.toms.planeTickets.entity.Flight;
import pl.toms.planeTickets.entity.Plane;
import pl.toms.planeTickets.entity.Seat;
import pl.toms.planeTickets.exception.NotFoundException;
import pl.toms.planeTickets.repository.FlightRepository;
import pl.toms.planeTickets.repository.PlaneRepository;
import pl.toms.planeTickets.repository.SeatRepository;

@Service
public class FlightService {
    protected static final Logger LOGGER = LoggerFactory.getLogger(FlightService.class);
    
    /**
     * Wiadomość przy błędzie {@link NotFoundException}
     */
    private static String message = "There is no flights with id: ";

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private PlaneRepository planeRepository;

	@Autowired
	private SeatRepository seatRepository;
	
	public List<Flight> getAllFlights() {
		return flightRepository.findAll();
	}

	public Flight getFlight(Integer flightId) {
		Flight flight = flightRepository.findOneById(flightId);
		if (flight == null) {
		    message += flightId;
		    LOGGER.error(message);
            throw new NotFoundException(message);
		}
		return flight;
	}

	public Flight addFlight(Flight flight) {
		Flight newFlight = flightRepository.save(flight);
		buildFlightSeats(newFlight);
		Integer newFlightId = newFlight.getId();
		LOGGER.debug("Created new flight with id: " + newFlightId);
		return flightRepository.findOneById(newFlightId); //FIXME nie zwraca seats!
	}

	public Flight updateFlight(Flight flight) {
		return flightRepository.save(flight);
	}

	public void deleteFlight(int flightId) {
		if (flightRepository.findOneById(flightId) == null) {
	          message += flightId;
	          LOGGER.error(message);
	          throw new NotFoundException(message);
		}
		flightRepository.deleteById(flightId);
		LOGGER.debug("Deleted flight with id: " + flightId);
	}

	private void buildFlightSeats(Flight flight) {
		Plane plane = flight.getPlane();
		if (plane == null) {
		    String message = "There is no information about the plane";
		    LOGGER.error(message);
            throw new NotFoundException(message);
		}

		Integer seatsRows = plane.getSeatsRows();
		if (seatsRows == null || seatsRows == 0) {
			Integer planeId = plane.getId();
			plane = planeRepository.findOneById(planeId);
			seatsRows = plane.getSeatsRows();
		}

		int seatsInRow = plane.getSeatsInRow();
		int seatNumber = 0;
		for (int i = 0; i < seatsInRow; i++) {
			for (int j = 0; j < seatsRows; j++) {
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
