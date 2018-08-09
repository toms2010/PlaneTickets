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

    @GetMapping
    public List<Flight> getAllFlights() {
	return flightService.getAllFlights();
    }

    @GetMapping("/{flightId}")
    public Flight getFlight(@PathVariable int flightId) {
	return flightService.getFlight(flightId);
    }

    @PostMapping
    public ResponseEntity<Flight> addFlight(@Valid @RequestBody Flight flight) {
	Flight newFlight = flightService.addFlight(flight);
	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newFlight.getId())
		.toUri();
	return ResponseEntity.created(location).body(newFlight);
    }

    @PutMapping
    public ResponseEntity<URI> updateFlight(@Valid @RequestBody Flight flight) {
	Flight updatedFlight = flightService.updateFlight(flight);
	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(updatedFlight.getId()).toUri();
	//TODO 304 (Not Modified)
	return ResponseEntity.ok(location);
    }

    @DeleteMapping("/{flightId}")
    public ResponseEntity<Flight> deleteFlight(@PathVariable int flightId) {
	flightService.deleteFlight(flightId);
	return ResponseEntity.noContent().build();
    }
}