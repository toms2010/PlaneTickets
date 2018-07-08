package pl.toms.plane.repository;

import org.springframework.data.repository.CrudRepository;

import pl.toms.plane.entity.Plane;

public interface PlaneRepository extends CrudRepository<Plane, Integer>{

	Plane findOneById(Integer id);
}
