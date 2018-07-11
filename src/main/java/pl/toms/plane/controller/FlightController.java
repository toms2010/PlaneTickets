package pl.toms.plane.controller;

import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pl.toms.plane.entity.Flight;
import pl.toms.plane.service.FlightService;

@RestController
@RequestMapping("api/")
public class FlightController {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);
	
	@Autowired
	private FlightService flightService;
	
	@GetMapping("/flight")
	public List<Flight> getFlights(){
		return flightService.getFlights();
	}	
	
	@GetMapping("/flight/{flightId}")
	public Flight getFlight(@PathVariable int flightId){
		return flightService.getFlight(flightId);
	}	
	
	@PostMapping("/flight")
	public ResponseEntity<?> addFlight(@Valid @RequestBody Flight flight) {        
		Flight newFlight = flightService.addFlight(flight);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/{id}").buildAndExpand(newFlight.getId()).toUri();
		return ResponseEntity.created(location).body(newFlight);
	}
	
	@PutMapping("/flight")
	public Flight updateFlight (@RequestBody Flight flight) {
		return flightService.updateFlight(flight);	
	}
	
	@DeleteMapping("/flight/{flightId}")
	public void deleteFlight(@PathVariable int flightId) {
		flightService.deleteFlight(flightId);
	}
	
	
}


