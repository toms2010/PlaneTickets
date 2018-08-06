package pl.toms.planeTickets.Controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    int otherId;
    
    @Before
    public void setUp() {
        flightId = Mockito.anyInt();
        otherId = Mockito.anyInt();
        for(;flightId == otherId;)
            otherId = Mockito.anyInt();
        
        Mockito.doNothing().when(flightService).deleteFlight(flightId);
    }
    
    /**
     * Test sprawdzający czy zostanie usunięty lot pod zapytaniem <b>DELETE</b> <code>/api/flights/1 </code>
     * oraz czy status http będzie równy 200
     * @throws Exception
     */
    @Test
    public void retrieveSingleFlightWhenIdIsOne() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/flights/" + flightId).accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(requestBuilder).andExpect(status().isNoContent());
    } 
        
    /**
     * Test sprawdzający czy nie zostanie usunięty lot pod zapytaniem <b>DELETE</b> <code>/api/flights/3 </code>
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
