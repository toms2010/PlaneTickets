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

import pl.toms.planeTickets.entity.Plane;
import pl.toms.planeTickets.service.PlaneService;

@RestController
@RequestMapping("api/planes")
public class PlaneController {
    @Autowired
    private PlaneService planeService;

    @GetMapping
    public List<Plane> getPlanes() {
	return planeService.getPlanes();
    }

    @GetMapping("/{planeTypeId}")
    public Plane getPlane(@PathVariable int planeTypeId) {
	return planeService.getPlane(planeTypeId);
    }

    @PostMapping
    public ResponseEntity<Plane> addPlaneType(@Valid @RequestBody Plane plane) {
	Plane newPlane = planeService.addPlaneType(plane);

	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlane.getId())
		.toUri();

	return ResponseEntity.created(location).body(newPlane);
    }

    @PutMapping
    public ResponseEntity<URI> updatePlane(@RequestBody Plane plane) {
	Plane updatedPlane = planeService.addPlaneType(plane);

	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(updatedPlane.getId()).toUri();

	return ResponseEntity.ok(location);
    }

    @DeleteMapping("/{planeTypeId}")
    public ResponseEntity<Plane> deletePlane(@PathVariable int planeTypeId) {
	planeService.deletePlane(planeTypeId);

	return ResponseEntity.noContent().build();
    }
}
