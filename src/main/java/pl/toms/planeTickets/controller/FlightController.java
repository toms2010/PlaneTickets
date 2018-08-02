package pl.toms.planeTickets.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("api/")
public class FlightController {
	// TODO logowanie , javadoc
	protected static final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);

	@Autowired
	private FlightService flightService;

	@GetMapping("/flights")
	public List<Flight> getAllFlights() {
		return flightService.getAllFlights();
	}

	@GetMapping("/flights/{flightId}")
	public Flight getFlight(@PathVariable int flightId) {
		return flightService.getFlight(flightId);
	}

	@PostMapping("/flights") // TODO Pomyśleć na Optional
	public ResponseEntity<Flight> addFlight(@Valid @RequestBody Flight flight) {
		Flight newFlight = flightService.addFlight(flight);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newFlight.getId())
				.toUri();

		return ResponseEntity.created(location).body(newFlight);
	}

	@PutMapping("/flights")
	public ResponseEntity<URI> updateFlight(@Valid @RequestBody Flight flight) {
		Flight updatedFlight = flightService.updateFlight(flight);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(updatedFlight.getId()).toUri();
///304 (Not Modified)
		return ResponseEntity.ok(location);
	}

	@DeleteMapping("/flights/{flightId}")
	public ResponseEntity<Flight> deleteFlight(@PathVariable int flightId) {
		flightService.deleteFlight(flightId);

		return ResponseEntity.noContent().build();
	}
}