package pl.toms.plane.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.toms.plane.entity.Plane;
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
		System.out.println(plane);
		return plane; 
	}

	public Plane addPlaneType(Plane plane) {
		plane = planeRepository.save(plane);
		return plane;
	}

	public void deletePlane(int planeTypeId) {
		planeRepository.deleteById(planeTypeId);	
	}

	public Plane updatePlane(Plane plane) {
		// TODO Auto-generated method stub
		return null;
	}

}
