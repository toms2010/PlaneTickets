package pl.toms.planeTickets.Controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import pl.toms.planeTickets.controller.FlightController;
import pl.toms.planeTickets.entity.Flight;
import pl.toms.planeTickets.entity.Plane;
import pl.toms.planeTickets.service.FlightService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FlightController.class, secure = false)
public class FlightController_AddFlightTest
{
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(),
        Charset.forName("utf8"));
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean 
    private FlightService flightService;
    
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private Flight mockFlight;
    
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    
    @Before
    public void setUp() {
        Plane mockPlane = new  Plane();
        mockPlane.setBrand("Airbus");
        mockPlane.setId(1);
        mockPlane.setModel("A-380");
        mockPlane.setSeatsInRow(8);
        mockPlane.setSeatsRows(60);
        
        mockFlight = new Flight();
        mockFlight.setFlightNumber("ABC123");
        mockFlight.setDepartureAirport("KTW");
        mockFlight.setArrivalAirport("KKK");
        mockFlight.setDepartureDate(LocalDateTime.of(2018, 07, 29, 15, 35));
        mockFlight.setFlightTime(LocalTime.of(2, 50));
        //mockFlight.setPlane(mockPlane);
                
        Mockito.when(flightService.addFlight(mockFlight)).thenReturn(mockFlight);
    }
    
    /**
     * Test sprawdzający czy zostanie dodany i zwrócony lot pod zapytaniem <code>/api/flights </code>
     * oraz czy status http będzie równy 200
     * @throws Exception
     */
    @Test
    public void postFlightWhenDataIsOk() throws Exception {
        //String expected = "{\"message\":\"Validation Failed\", 'flightNumber': rejected value [null]}";
        String flight = json(mockFlight);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/flights").contentType(contentType).content(flight);
        
       mockMvc.perform(requestBuilder).andExpect(status().isCreated());

        //JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
    
    /**
     * Test sprawdzający czy nie zostanie dodany lot pod zapytaniem <code>/api/flights </code> gdy podamy za długi kod lotniska
     * oraz czy status http będzie równy 422
     * @throws Exception
     */
    @Test
    public void postFlightWhenDepartureAirportIsWrong() throws Exception {
        String expected = "{message:\"Validation Failed\", details:\"org.springframework.validation.BeanPropertyBindingResult: 1 errors\\nField error in object 'flight' on field 'departureAirport': rejected value [AWWA]; codes [Size.flight.departureAirport,Size.departureAirport,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: "
            + "codes [flight.departureAirport,departureAirport]; arguments []; default message [departureAirport],3,3]; default message [size must be between 3 and 3]\"}";
        mockFlight.setDepartureAirport("AWWA");
        String flight = json(mockFlight);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/flights").contentType(contentType).content(flight);
        
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    } 
    
    /**
     * Test sprawdzający czy nie zostanie dodany lot pod zapytaniem <code>/api/flights </code> gdy podamy za krótki kod lotniska
     * oraz czy status http będzie równy 422
     * @throws Exception
     */
    @Test
    public void postFlightWhenArrivalAirportIsWrong() throws Exception {
        String expected = "{message:\"Validation Failed\", details:\"org.springframework.validation.BeanPropertyBindingResult: 1 errors\\nField error in object 'flight' on field 'departureAirport': rejected value [AA]; codes [Size.flight.departureAirport,Size.departureAirport,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [flight.departureAirport,departureAirport]; arguments []; default message [departureAirport],3,3]; default message [size must be between 3 and 3]\"}";
        mockFlight.setDepartureAirport("AA");
        String flight = json(mockFlight);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/flights").contentType(contentType).content(flight);
        
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    } 
    
    /**
     * Test sprawdzający czy nie zostanie dodany lot pod zapytaniem <code>/api/flights </code> gdy nie podamy numeru lotu
     * oraz czy status http będzie równy 422
     * @throws Exception
     */
    @Test
    public void postFlightWhenFlightNumberIsMissing() throws Exception {
        String expected = "{message:\"Validation Failed\", details:\"org.springframework.validation.BeanPropertyBindingResult: 1 errors\\nField error in object 'flight' on field 'flightNumber': rejected value []; codes [NotEmpty.flight.flightNumber,NotEmpty.flightNumber,NotEmpty.java.lang.String,NotEmpty]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [flight.flightNumber,flightNumber]; arguments []; default message [flightNumber]]; default message [must not be empty]\"}";
        mockFlight.setFlightNumber("");
        String flight = json(mockFlight);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/flights").contentType(contentType).content(flight);
        
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    } 
    
    
    protected String json(Flight flight) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(flight, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
