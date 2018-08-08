package pl.toms.planeTickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.toms.planeTickets.entity.Flight;
import pl.toms.planeTickets.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer>{

    Seat findOneById(Integer id);
    
    List<Seat> findAllByFlight(Flight flight);
    
    Seat findOneByNumberAndFlight(Integer number, Flight flight);
    
}
