package pl.toms.planeTickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.toms.planeTickets.entity.Seat;
import pl.toms.planeTickets.service.SeatService;

@RestController
@RequestMapping("api/flights/{flightId}/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    /**
     * Pobiera listę miejc w locie o podanym identyfikatorze
     * @param flightId identyfikator lotu
     * @return lista miejsc
     */
    @GetMapping
    public List<Seat> getSeats(@PathVariable int flightId) {
	return seatService.getSeats(flightId);
    }

    /**
     * Pobiera miejsce o podanym numerze w locie o podanym identyfikatrze 
     * @param flightId identyfikator lotu
     * @param seatNumber numer miejsca w samolocie
     * @return miejsce o podanym numerze
     */
    @GetMapping("/{seatNumber}")
    public Seat getSeat(@PathVariable int flightId, @PathVariable int seatNumber) {
	return seatService.getSeat(flightId, seatNumber);
    }

    /**
     * Rezerwuje miejsce o podanym numerze
     * @param flightId identyfikator lotu
     * @param seatNumber numer miejsca w samolocie
     * @return zarezerwowane miejsce
     */
    @PutMapping("/{seatNumber}")
    public Seat reservateSeat(@PathVariable int flightId, @PathVariable int seatNumber) {
	return seatService.reservateSeat(seatService.getSeat(flightId, seatNumber));
    }
}
