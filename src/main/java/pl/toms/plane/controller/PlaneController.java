package pl.toms.plane.controller;

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

import pl.toms.plane.entity.Plane;
import pl.toms.plane.service.PlaneService;

@RestController
@RequestMapping("api/")
public class PlaneController {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PlaneController.class);

	@Autowired
	private PlaneService planeService;

	@GetMapping("/plane")
	public List<Plane> getPlanes() {
		return planeService.getPlanes();
	}

	@GetMapping("/plane/{planeTypeId}")
	public Plane getPlane(@PathVariable int planeTypeId) {
		return planeService.getPlane(planeTypeId);
	}

	@PostMapping("/plane")
	public ResponseEntity<Plane> addPlaneType(@Valid @RequestBody Plane plane) {
		Plane newPlane = planeService.addPlaneType(plane);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlane.getId())
				.toUri();

		return ResponseEntity.created(location).body(newPlane);
	}

	@PutMapping("/plane")
	public ResponseEntity<URI> updatePlane(@RequestBody Plane plane) {
		Plane updatedPlane = planeService.addPlaneType(plane);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(updatedPlane.getId()).toUri();

		return ResponseEntity.ok(location);
	}

	@DeleteMapping("/plane/{planeTypeId}")
	public ResponseEntity<Plane> deletePlane(@PathVariable int planeTypeId) {
		planeService.deletePlane(planeTypeId);

		return ResponseEntity.noContent().build();
	}
}
