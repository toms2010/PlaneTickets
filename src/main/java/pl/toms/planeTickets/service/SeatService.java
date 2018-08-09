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

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private FlightRepository flightRepository;

    /**
     * Pobiera listę miejsc w locie o podanym identyfikatorze
     * 
     * @param flightId identyfikator lotu
     * @return lista miejsc
     * @throws NotFoundException
     */
    public List<Seat> getSeats(Integer flightId) {
        Flight flight = getFlightById(flightId);
        return seatRepository.findAllByFlight(flight);
    }
    
    /**
     * Pobiera miejsce o podanym numerze w locie o podanym identyfikatrze
     * 
     * @param flightId identyfikator lotu
     * @param seatNumber numer miejsca w samolocie
     * @return miejsce o podanym numerze
     * @throws NotFoundException
     */
    public Seat getSeat(Integer flightId, Integer seatNumber) {
        Flight flight = getFlightById(flightId);
        Seat seat = seatRepository.findOneByNumberAndFlight(seatNumber, flight);
        if (seat == null) {
            MessageFormat form = new MessageFormat("There is no seat with number: {0} in flight with id: {1}");
            Object[] testArgs = {seatNumber, flight.getId()};
            String info = form.format(testArgs);
            LOGGER.error(info);
            throw new NotFoundException(info);
        }
        return seat;
    }

    /**
     * Zmienia status przekazanego miejsca oraz dopisuje/usuwa pasażera
     * @param seat obiekt miejsca
     * @return zmodyfikowane miejsce
     */
    public Seat changeSeatStatus(int flightId, Seat seat) {
        Flight flight = getFlightById(flightId);
        Seat oldSeat = seatRepository.findOneByNumberAndFlight(seat.getNumber(), flight);
        seatCheck(oldSeat, seat);
        
        String seatStatus = seat.getStatus();
        oldSeat.setStatus(seatStatus);
        //if (SeatseatStatus) // TODO JAk anulowanie to name null
        oldSeat.setPassagerName(seat.getPassagerName());
        seatRepository.save(oldSeat);
        
        if (Seat.SeatStatus.R.getStatus().equals(oldSeat.getStatus())) {
            LOGGER.debug("Reservated seat with number: " + oldSeat.getNumber());
        } else
            LOGGER.debug("Canceled reservation for seat with number: " + oldSeat.getNumber());
        return oldSeat;

    }

    /**
     * Pobiera lot po przekazanym identyfikatorze a następnie sprawdza czy taki lot istnieje
     * @param flightId identyfikator lotu
     * @return obiekt lotu
     */
    private Flight getFlightById(int flightId) {
        MessageFormat form = new MessageFormat("There is no flights with id: {0}.");
        Flight flight = flightRepository.findOneById(flightId);
        if (flight == null) {
            Object[] testArgs = {flightId};
            String info = form.format(testArgs);
            LOGGER.error(info);
            throw new NotFoundException(info);
        }
        return flight;
    }

    /**
     * Sprawdza czy można zarezerwować bądz usunąć rezerwację dla przekazanego miejsca.
     * 
     * @param seat miejsce do zmiany statusu
     * @return true jeśli można zmienić status
     */
    private void seatCheck(Seat oldSeat, Seat seat) {
        MessageFormat form = new MessageFormat("Seat with number {0} is {1}");
        if (Seat.SeatStatus.N.getStatus().equals(oldSeat.getStatus())) {
            Object[] testArgs = { oldSeat.getNumber(), Seat.SeatStatus.N.getName() };
            String info = form.format(testArgs);
            LOGGER.debug(info);
            throw new ApplicationException(info, HttpStatus.CONFLICT);
        }
        if (seat.getStatus().equals(oldSeat.getStatus())) {
            Object[] testArgs = { oldSeat.getNumber(), SeatStatus.valueOf(seat.getStatus()).getName() };
            String info = form.format(testArgs);
            LOGGER.debug(info);
            throw new ApplicationException(info, HttpStatus.CONFLICT);
        }
    }
}
