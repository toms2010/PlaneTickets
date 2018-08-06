package pl.toms.planeTickets.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.toms.planeTickets.entity.Seat;
import pl.toms.planeTickets.entity.Seat.SeatStatus;
import pl.toms.planeTickets.exception.NotFoundException;
import pl.toms.planeTickets.repository.SeatRepository;

@Service
public class SeatService {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SeatService.class);
    
    /**
     * Wiadomość przy błędzie {@link NotFoundException}
     */
    private static String message = "There is no seat with id: ";

    @Autowired
    private SeatRepository seatRepository;
    
	public List<Seat> getSeats() { //TODO pobrac id lotu
	    return (List<Seat>) seatRepository.findAll();
	}

	public Seat getSeat(int seatId) {
	    Seat seat = seatRepository.findOneById(seatId);
        if (seat == null) {
            message += seatId;
            LOGGER.error(message);
            throw new NotFoundException(message);
        }
        return seat;
	}

	public Seat reservateSeat(Seat seat) {
	    seat.setStatus(SeatStatus.R.getStatus());
	    LOGGER.debug("Reservated with id: " + seat.getId());
	    return seat;
	}
}
