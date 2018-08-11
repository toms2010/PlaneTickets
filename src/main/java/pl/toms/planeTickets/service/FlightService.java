package pl.toms.planeTickets.service;

import java.text.MessageFormat;
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
    MessageFormat form = new MessageFormat("There is no flights with id: {0}.");

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private SeatRepository seatRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlight(int flightId) {
        Flight flight = flightRepository.findOneById(flightId);
        if (flight == null) {
            Object[] testArgs = { flightId };
            String info = form.format(testArgs);
            LOGGER.error(info);
            throw new NotFoundException(info);
        }
        return flight;
    }

    public Flight addFlight(Flight flight) {
        Flight newFlight = flightRepository.save(flight);
        buildFlightSeats(newFlight);
        Integer newFlightId = newFlight.getId();
        LOGGER.debug("Created new flight with id: " + newFlightId);
        return flightRepository.findOneById(newFlightId); // FIXME nie zwraca seats!
    }

    public Flight updateFlight(Flight flight) {
        LOGGER.debug("Updated flight with id: " + flight.getId());
        return flightRepository.save(flight);
    }

    public void deleteFlight(int flightId) {
        if (flightRepository.findOneById(flightId) == null) {
            Object[] testArgs = { flightId };
            String info = form.format(testArgs);
            LOGGER.error(info);
            throw new NotFoundException(info);
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
                seatNumber++;
                seat.setFlight(flight);
                seat.setNumber(seatNumber);
                seat.setStatus(Seat.SeatStatus.F.getStatus());
                seatRepository.save(seat);
            }
        }
    }
}
