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

    /**
     * Pobiera wszystkie rodzaje samolotów.
     * 
     * @return lista ze wszystkimi rodzajami samolotów
     */
    @GetMapping
    public List<Plane> getPlanes() {
        return planeService.getPlanes();
    }

    /**
     * Pobiera rodzaj samolotu o podanym identyfikatorze.
     * 
     * @param planeTypeId identyfikator rodzaju samolotu
     * @return obiekt rodzaju samolotu
     */
    @GetMapping("/{planeTypeId}")
    public Plane getPlane(@PathVariable int planeTypeId) {
        return planeService.getPlane(planeTypeId);
    }

    /**
     * Zapisuje przekazany rodzaj samolotu.
     * 
     * @param plane obiekt rodzaju samolotu do zapisania
     * @return odpowiedz zawierająca zapisany obiekt, status odpowiedzi oraz linki.
     */
    @PostMapping
    public ResponseEntity<Plane> addPlaneType(@Valid @RequestBody Plane plane) {
        Plane newPlane = planeService.addPlaneType(plane);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPlane.getId())
                .toUri();
        return ResponseEntity.created(location).body(newPlane);
    }

    /**
     * Zapisuje zmiany w przakazanym obiekcie do bazy danych.
     * 
     * @param plane obiekt ze zmianami
     * @return odpowiedz zawierająca status odpowiedzi oraz linki.
     */
    @PutMapping
    public ResponseEntity<URI> updatePlane(@RequestBody Plane plane) {
        Plane updatedPlane = planeService.addPlaneType(plane);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(updatedPlane.getId()).toUri();
        return ResponseEntity.ok(location);
    }

    /**
     * Usuwa rodzaj samolotu o wskazanym identyfikatorze.
     * 
     * @param planeTypeId identyfikator rodzaju samolotu
     * @return odpowiedz noContent
     */
    @DeleteMapping("/{planeTypeId}")
    public ResponseEntity<Plane> deletePlane(@PathVariable int planeTypeId) {
        planeService.deletePlane(planeTypeId);
        return ResponseEntity.noContent().build();
    }
}
