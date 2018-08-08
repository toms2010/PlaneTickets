package pl.toms.planeTickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.toms.planeTickets.entity.Plane;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Integer>{

	Plane findOneById(Integer id);
}
