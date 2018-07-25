package pl.toms.plane.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.toms.plane.entity.Plane;
import pl.toms.plane.exception.NotFoundException;
import pl.toms.plane.repository.PlaneRepository;

@Service
public class PlaneService {

	@Autowired
	private PlaneRepository planeRepository;
	
	public List<Plane> getPlanes() {
		return (List<Plane>) planeRepository.findAll();
	}

	public Plane getPlane(int planeTypeId) {
		Plane plane = planeRepository.findOneById(planeTypeId);
		if (plane == null)
			throw new NotFoundException("There is no plane with id: " + planeTypeId);
		
		return plane; 
	}

	public Plane addPlaneType(Plane plane) {
		return planeRepository.save(plane);
	}

	public void deletePlane(int planeTypeId) {
		if (planeRepository.findOneById(planeTypeId) == null)
			throw new NotFoundException("There is no plane with id: " + planeTypeId);
		
		planeRepository.deleteById(planeTypeId);	
	}
}
