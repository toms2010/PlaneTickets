package pl.toms.planeTickets.Controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import pl.toms.planeTickets.controller.FlightController;
import pl.toms.planeTickets.entity.Flight;
import pl.toms.planeTickets.service.FlightService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FlightController.class, secure = false)
public class FlightController_GetFlightTest
{
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean 
    private FlightService flightService;
    
    @Before
    public void setUp() {
        Flight mockFlightsFirst = new Flight();
        mockFlightsFirst.setId(1);
        mockFlightsFirst.setFlightNumber("ABC123");
        mockFlightsFirst.setDepartureAirport("KTW");
        mockFlightsFirst.setArrivalAirport("WWA");
        mockFlightsFirst.setDepartureDate(LocalDateTime.of(2018, 07, 29, 15, 35));
        mockFlightsFirst.setFlightTime(LocalTime.of(2, 50));
        
        Flight mockFlightsSecond = new Flight();
        mockFlightsSecond.setId(2);
        mockFlightsSecond.setFlightNumber("ABC567");
        mockFlightsSecond.setDepartureAirport("KTZ");
        mockFlightsSecond.setArrivalAirport("WAB");
        mockFlightsSecond.setDepartureDate(LocalDateTime.of(2018, 8, 01, 15, 35));
        mockFlightsSecond.setFlightTime(LocalTime.of(5, 15));
        
        List<Flight> allFlights = new ArrayList<>();
        allFlights.add(mockFlightsFirst);
        allFlights.add(mockFlightsSecond);
        
        Mockito.when(flightService.getFlight(Mockito.eq(1))).thenReturn(mockFlightsFirst);
        Mockito.when(flightService.getFlight(Mockito.eq(2))).thenReturn(mockFlightsSecond);
        Mockito.when(flightService.getFlight(Mockito.eq(3))).thenThrow(new pl.toms.planeTickets.exception.NotFoundException());
        Mockito.when(flightService.getAllFlights()).thenReturn(allFlights);
    }
    
    /**
     * Test sprawdzający czy zostanie zwrócony pierwszy lot pod zapytaniem <code>/api/flights/1 </code>
     * oraz czy status http będzie równy 200
     * @throws Exception
     */
    @Test
    public void retrieveSingleFlightWhenIdIsOne() throws Exception {
        String expected = "{id:1, flightNumber:ABC123, departureAirport:KTW}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/flights/1").accept(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    } 
    
    /**
     * Test sprawdzający czy zostanie zwrócony drugi lot pod zapytaniem <code>/api/flights/2 </code>
     * oraz czy status http będzie równy 200
     * @throws Exception
     */
    @Test
    public void retrieveSingleFlightWhenIdIsTwo() throws Exception {
        String expected = "{id:2, flightNumber:ABC567, departureAirport:KTZ}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/flights/2").accept(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    } 
    
    /**
     * Test sprawdzający czy nie zostanie zwrócony lot pod zapytaniem <code>/api/flights/3 </code>
     * oraz czy status http będzie równy 404
     * @throws Exception
     */
    @Test
    public void retrieveSingleFlightWhenIdIsThree() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/flights/3").accept(MediaType.APPLICATION_JSON);
         
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());  
    } 
    
    /**
     * Test sprawdzający czy nie zostanie zwrócony lot pod zapytaniem <code>/api/flights/abc </code>
     * oraz czy status http będzie równy 400
     * @throws Exception
     */
    @Test
    public void retrieveSingleFlightWhenIdIsCointeinsLetters() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/flights/abc").accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());  
    } 
    
    /**
     * Test sprawdzający czy zostaną zwrócone dwa loty pod zapytaniem <code>/api/flights </code>
     * oraz czy status http będzie równy 200
     * @throws Exception
     */
    @Test
    public void retrieveAllFlight() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/flights").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].flightNumber", is("ABC123")))
            .andExpect(jsonPath("$[0].departureAirport", is("KTW")))
            .andExpect(jsonPath("$[0].arrivalAirport", is("WWA")))
            .andExpect(jsonPath("$[0].departureDate", is("2018-07-29T15:35:00")))
            .andExpect(jsonPath("$[0].flightTime", is("02:50:00")))
            .andExpect(jsonPath("$[1].id", is(2)))
            .andExpect(jsonPath("$[1].flightNumber", is("ABC567")))
            .andExpect(jsonPath("$[1].departureAirport", is("KTZ")))
            .andExpect(jsonPath("$[1].arrivalAirport", is("WAB")))
            .andExpect(jsonPath("$[1].departureDate", is("2018-08-01T15:35:00")))
            .andExpect(jsonPath("$[1].flightTime", is("05:15:00")))
            .andReturn();
    } 
}
