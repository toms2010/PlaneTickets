package pl.toms.planeTickets.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import pl.toms.planeTickets.entity.Flight;
import pl.toms.planeTickets.entity.Seat;

@Repository
public interface SeatRepository extends BaseRepository<Seat, Integer> {

    List<Seat> findAllByFlight(Flight flight);

    Seat findOneByNumberAndFlight(Integer number, Flight flight);
}
