package pl.toms.plane.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.toms.plane.entity.Plane;
import pl.toms.plane.service.PlaneService;

@RestController
@RequestMapping("api/")
public class PlaneController {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(PlaneController.class);

	@Autowired
	private PlaneService planeService;
	
	@GetMapping("/plane")
	public List<Plane> getPlanes(){
		return planeService.getPlanes();
	}	
	
	@GetMapping("/plane/{planeTypeId}")
	public Plane getPlane(@PathVariable int planeTypeId){
		return planeService.getPlane(planeTypeId);
	}	
	
	@PostMapping("/plane")
	public Plane addPlaneType(@RequestBody Plane plane) {
		return planeService.addPlaneType(plane);		
	}
	
	@PutMapping("/plane")
	public Plane updatePlane (@RequestBody Plane plane) {
		return planeService.updatePlane(plane);	
	}
	
	@DeleteMapping("/plane/{planeTypeId}")
	public void deletePlane(@PathVariable int planeTypeId) {
		//Plane planeTemp = planeService.getPlane(planeTypeId);
		//TODO
		planeService.deletePlane(planeTypeId);
	}
}
