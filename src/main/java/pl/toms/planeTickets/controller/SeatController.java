package pl.toms.planeTickets.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.toms.planeTickets.entity.Seat;
import pl.toms.planeTickets.service.SeatService;

@RestController
@RequestMapping("api/planes/{planeTypeId}/")
public class SeatController {

	protected static final Logger LOGGER = LoggerFactory.getLogger(SeatController.class);
	
	@Autowired
	private SeatService seatService;
	
	@GetMapping("/seats")
	public List<Seat> getSeats(@PathVariable int planeTypeId){
		return seatService.getSeats();
	}
	
	@GetMapping("/seats/{seatNumber}")
	public Seat getSeat(@PathVariable int planeTypeId, @PathVariable int seatNumber){
		return seatService.getSeat(seatNumber);
	}
	
	@PutMapping("/seats")
	public Seat reservateSeat(@PathVariable int planeTypeId, @RequestBody Seat seat) {
		return seatService.reservateSeat(seat);	
	}
}