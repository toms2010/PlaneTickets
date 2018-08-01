package pl.toms.planeTickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.toms.planeTickets.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer>{

}
