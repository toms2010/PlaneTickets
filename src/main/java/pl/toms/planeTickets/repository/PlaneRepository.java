package pl.toms.planeTickets.repository;

import org.springframework.stereotype.Repository;

import pl.toms.planeTickets.entity.Plane;

@Repository
public interface PlaneRepository extends BaseRepository<Plane, Integer> {

}
