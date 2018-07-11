package pl.toms.plane.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.toms.plane.entity.Plane;

public interface PlaneRepository extends JpaRepository<Plane, Integer>{

	Plane findOneById(Integer id);
}
