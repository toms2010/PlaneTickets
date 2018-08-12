package pl.toms.planeTickets.service;

import java.text.MessageFormat;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import pl.toms.planeTickets.entity.Flight;
import pl.toms.planeTickets.entity.Plane;
import pl.toms.planeTickets.entity.Seat;
import pl.toms.planeTickets.entity.Seat.SeatStatus;
import pl.toms.planeTickets.exception.ApplicationException;
import pl.toms.planeTickets.exception.NotFoundException;
import pl.toms.planeTickets.repository.FlightRepository;
import pl.toms.planeTickets.repository.PlaneRepository;
import pl.toms.planeTickets.repository.SeatRepository;

@Service
@Transactional
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

    /**
     * Pobiera wszystkie loty.
     * 
     * @return lista ze wszystkimi lotami
     */
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    /**
     * Pobiera lot o podanym identyfikatorze.
     * 
     * @param flightId identyfikator lotu
     * @return obiekt lotu
     */
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

    /**
     * Zapisuje przekazany lot. Na podstawie przekazanego typu samolotu tworzy miejsca w locie.
     * 
     * @param flight obiekt lotu do zapisania
     * @return zapisany obiekt lotu
     */
    public Flight addFlight(Flight flight) {
        Flight newFlight = flightRepository.save(flight);
        buildFlightSeats(newFlight);
        Integer newFlightId = newFlight.getId();
        LOGGER.debug("Created new flight with id: " + newFlightId);
        return flightRepository.findOneById(newFlightId); // FIXME nie zwraca seats!
    }

    /**
     * Zapisuje zmiany w przakazanym locie do bazy danych.
     * 
     * @param flight obiekt ze zmianami
     * @return zaktualizowany lot
     * @throws ApplicationException gdy w locie są juz zarezerwowane jakieś miejsca
     */
    public Flight updateFlight(Flight flight) {
        Flight oldFlight = flightRepository.findOneById(flight.getId());
        List<Seat> seats = oldFlight.getSeats();
        flight.setSeats(seats);
        if (!seats.isEmpty()) {
            for (Seat seat : seats) {
                if (SeatStatus.R.getStatus().equals(seat.getStatus())) {
                    throw new ApplicationException("Can not change flight with reserved seats", HttpStatus.CONFLICT);
                }
            }
            flight.getSeats().clear();
        }
        Flight upadtedFlight = flightRepository.save(flight);
        buildFlightSeats(upadtedFlight);
        LOGGER.debug("Updated flight with id: " + upadtedFlight.getId());
        return upadtedFlight;
    }

    /**
     * Usuwa lot o wskazanym identyfikatorze. Wraz z lotem są również usuwane przypisane do niego miejsca. 
     * 
     * @param flightId identyfikator lotu
     */
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

    /**
     * Na podstawie typu samolotu buduje miejsca w locie.
     * @param flight obiekt lotu
     */
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
