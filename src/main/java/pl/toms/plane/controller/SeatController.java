package pl.toms.plane.controller;

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

import pl.toms.plane.entity.Seat;
import pl.toms.plane.service.SeatService;

@RestController
@RequestMapping("api/plane/{planeTypeId}/")
public class SeatController {

	protected static final Logger LOGGER = LoggerFactory.getLogger(SeatController.class);
	
	@Autowired
	private SeatService seatService;
	
	@GetMapping("/seat")
	public List<Seat> getSeats(@PathVariable int planeTypeId){
		return seatService.getSeats();
	}
	
	@GetMapping("/seat/{seatNumber}")
	public Seat getSeat(@PathVariable int planeTypeId, @PathVariable int seatNumber){
		return seatService.getSeat(seatNumber);
	}
	
	@PutMapping("/seat")
	public Seat reservateSeat(@PathVariable int planeTypeId, @RequestBody Seat seat) {
		return seatService.reservateSeat(seat);	
	}
}
