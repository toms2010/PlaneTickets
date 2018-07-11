package pl.toms.plane.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.toms.plane.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer>{

}
