package pl.toms.planeTickets.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import pl.toms.planeTickets.repository.FlightRepository;

@RunWith(SpringRunner.class)
public class FlightService_getFlightTest
{
    @Autowired 
    private FlightService flightService;
    
    @MockBean
    private FlightRepository flightRepository;
    
    
}
