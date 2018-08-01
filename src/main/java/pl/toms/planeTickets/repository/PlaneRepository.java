package pl.toms.planeTickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.toms.planeTickets.entity.Plane;

public interface PlaneRepository extends JpaRepository<Plane, Integer>{

	Plane findOneById(Integer id);
}
