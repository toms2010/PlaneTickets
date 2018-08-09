package pl.toms.planeTickets.service;

import java.text.MessageFormat;
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
    MessageFormat form = new MessageFormat("There is no flights with id: {0}.");

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private FlightRepository flightRepository;

    public List<Seat> getSeats(Integer flightId) {
	Flight flight = flightRepository.findOneById(flightId);
	if (flight == null) {
	    Object[] testArgs = {new Integer(flightId)};
        String info = form.format(testArgs);
	    LOGGER.error(info);
	    throw new NotFoundException(info);
	}
	return seatRepository.findAllByFlight(flight);
    }

    public Seat getSeat(Integer flightId, Integer seatNumber) {
	Flight flight = flightRepository.findOneById(flightId);
	if (flight == null) {
	    Object[] testArgs = {new Integer(flightId)};
        String info = form.format(testArgs);
        LOGGER.error(info);
        throw new NotFoundException(info);
	}
	Seat seat = seatRepository.findOneByNumberAndFlight(seatNumber, flight);
	if (seat == null) {
	    MessageFormat form = new MessageFormat("There is no seat with number: {0} in flight with id: {1}");
	    Object[] testArgs = {new Integer(seatNumber), new Integer(flight.getId())};
	    String info = form.format(testArgs);
	    LOGGER.error(info);
	    throw new NotFoundException(info);
	}
	return seat;
    }

    public Seat reservateSeat(Seat seat) {
	if (!Seat.SeatStatus.F.getStatus().equals(seat.getStatus())) {
	    String message = "Seat is not available: " + seat.getNumber();
	    LOGGER.debug(message);
	    throw new ApplicationException(message, HttpStatus.CONFLICT);
	}
	seat.setStatus(SeatStatus.R.getStatus());
	seatRepository.save(seat);
	LOGGER.debug("Reservated seat with number: " + seat.getNumber());
	return seat;
    }
}
