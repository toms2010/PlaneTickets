package pl.toms.planeTickets.Controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import pl.toms.planeTickets.controller.FlightController;
import pl.toms.planeTickets.service.FlightService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FlightController.class, secure = false)
public class FlightController_DeleteFlightTest
{
    @Autowired
    private MockMvc mockMvc;
    
    /** 
     * Serwis do kontroli zapytań flights 
     */
    @MockBean 
    private FlightService flightService;
    
    /** 
     * Identyfikator lotu 
     */
    int flightId;
    
    /** 
     * Błędny identyfikator lotu 
     */
    int otherId;
    
    @Before
    public void setUp() {
        Random generator = new Random();
        flightId = generator.nextInt();
        otherId = generator.nextInt();
        //zabezpieczenie przed wylosowaniem dwóch takich samych Id
        for(;flightId == otherId;)
            otherId = generator.nextInt();
        
        Mockito.doNothing().when(flightService).deleteFlight(Mockito.eq(flightId));
        Mockito.doThrow(new pl.toms.planeTickets.exception.NotFoundException()).when(flightService).deleteFlight(Mockito.eq(otherId));
    }
    
    /**
     * Test sprawdzający czy zostanie usunięty lot pod zapytaniem <b>DELETE</b> <code>/api/flights/{flightId} </code>
     * oraz czy status http będzie równy 200
     * @throws Exception
     */
    @Test
    public void retrieveSingleFlightWhenIdIsOne() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/flights/" + flightId).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isNoContent());
    } 
        
    /**
     * Test sprawdzający czy nie zostanie usunięty lot pod zapytaniem <b>DELETE</b> <code>/api/flights/{otherId} </code>
     * oraz czy status http będzie równy 404
     * @throws Exception
     */
    @Test
    public void retrieveSingleFlightWhenIdIsThree() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/flights/" + otherId).accept(MediaType.APPLICATION_JSON);
    
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());  
    } 
    
    /**
     * Test sprawdzający czy nie zostanie usunięty lot pod zapytaniem <b>DELETE</b> <code>/api/flights/abc </code>
     * oraz czy status http będzie równy 400
     * @throws Exception
     */
    @Test
    public void retrieveSingleFlightWhenIdIsCointeinsLetters() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/flights/abc").accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());  
    } 
}
