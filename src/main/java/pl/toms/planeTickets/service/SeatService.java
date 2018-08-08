package pl.toms.planeTickets.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import pl.toms.planeTickets.entity.Flight;
import pl.toms.planeTickets.entity.Seat;
import pl.toms.planeTickets.entity.Seat.SeatStatus;
import pl.toms.planeTickets.exception.ApplicationException;
import pl.toms.planeTickets.exception.NotFoundException;
import pl.toms.planeTickets.repository.FlightRepository;
import pl.toms.planeTickets.repository.SeatRepository;

@Service
public class SeatService {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SeatService.class);

    /**
     * Wiadomość przy błędzie lotu {@link NotFoundException}
     */
    private static String flightMessage = "There is no flights with id: ";
   
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private FlightRepository flightRepository;

    public List<Seat> getSeats(Integer flightId) {
	Flight flight = flightRepository.findOneById(flightId);
	if (flight == null) {
	    flightMessage += flightId;
	    LOGGER.error(flightMessage);
	    throw new NotFoundException(flightMessage);
	}
	return seatRepository.findAllByFlight(flight);
    }

    public Seat getSeat(Integer flightId, Integer seatNumber) {
	Flight flight = flightRepository.findOneById(flightId);
	if (flight == null) {
	    flightMessage += flightId;
	    LOGGER.error(flightMessage);
	    throw new NotFoundException(flightMessage);
	}
	Seat seat = seatRepository.findOneByNumberAndFlight(seatNumber, flight);
	if (seat == null) {
	    String message = "There is no seat with id: "+ seatNumber;
	    LOGGER.error(message);
	    throw new NotFoundException(message);
	}
	return seat;
    }

    public Seat reservateSeat(Seat seat) {
	if(!Seat.SeatStatus.F.getStatus().equals(seat.getStatus())){
	    String message = "Seat is not available: "+ seat.getNumber();
	    LOGGER.debug(message);
	    throw new ApplicationException(message, HttpStatus.CONFLICT) ;
	}
	seat.setStatus(SeatStatus.R.getStatus());
	seatRepository.save(seat);
	LOGGER.debug("Reservated seat with number: " + seat.getNumber());
	return seat;
    }
}
