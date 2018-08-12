package pl.toms.planeTickets.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pl.toms.planeTickets.entity.Flight;
import pl.toms.planeTickets.service.FlightService;

@RestController
@RequestMapping("api/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    /**
     * Pobiera wszystkie loty.
     * 
     * @return lista ze wszystkimi lotami
     */
    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    /**
     * Pobiera lot o podanym identyfikatorze.
     * 
     * @param flightId identyfikator lotu
     * @return obiekt lotu
     */
    @GetMapping("/{flightId}")
    public Flight getFlight(@PathVariable int flightId) {
        return flightService.getFlight(flightId);
    }

    /**
     * Zapisuje przekazany lot.
     * 
     * @param flight obiekt lotu do zapisania
     * @return odpowiez zawierająca zapisany obiekt, status odpowiedzi oraz linki.
     */
    @PostMapping
    public ResponseEntity<Flight> addFlight(@Valid @RequestBody Flight flight) {
        Flight newFlight = flightService.addFlight(flight);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newFlight.getId())
                .toUri();
        return ResponseEntity.created(location).body(newFlight);
    }

    /**
     * Zapisuje zmiany w przakazanym obiekcie do bazy danych.
     * 
     * @param flight obiekt ze zmianami
     * @return odpowiedz zawierająca status odpowiedzi oraz linki.
     */
    @PutMapping
    public ResponseEntity<Flight> updateFlight(@Valid @RequestBody Flight flight) {
        Flight updatedFlight = flightService.updateFlight(flight);
        return ResponseEntity.ok(updatedFlight);
    }

    /**
     * Usuwa lot o wskazanym identyfikatorze.
     * 
     * @param flightId identyfikator lotu
     * @return odpowiedz
     */
    @DeleteMapping("/{flightId}")
    public ResponseEntity<Flight> deleteFlight(@PathVariable int flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.noContent().build();
    }
}