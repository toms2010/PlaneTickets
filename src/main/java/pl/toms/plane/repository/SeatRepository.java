package pl.toms.plane.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.toms.plane.entity.Seat;

@Repository
public interface SeatRepository extends CrudRepository<Seat, Integer>{

}
