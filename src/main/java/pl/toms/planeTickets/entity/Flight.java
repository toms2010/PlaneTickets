package pl.toms.planeTickets.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "flight")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Flight extends BaseEntity {
    /**
     * 3 literowy kod lotniska.
     */
    @Column(name = "departure_airport")
    @Size(min = 3, max = 3)
    private String departureAirport;

    /**
     * 3 literowy kod lotniska.
     */
    @Column(name = "arrival_airport")
    @Size(min = 3, max = 3)
    private String arrivalAirport;

    /**
     * Numer lotu
     */
    @Column(name = "flight_number")
    @NotEmpty
    private String flightNumber;

    /**
     * Data wylotu
     */
    @Column(name = "departure_date")
    @NotNull
    private LocalDateTime departureDate;

    /**
     * Czas lotu.
     */
    @Column(name = "flight_time")
    @NotNull
    private LocalTime flightTime;

    /**
     * Samolot.
     */
    @ManyToOne
    @JoinColumn(name = "plane_type_id", nullable = false)
    @NotNull
    @JsonIdentityReference(alwaysAsId = true)
    private Plane plane;

    /**
     * Miejsca w samolocie podczas lotu.
     */
    @OneToMany(mappedBy = "flight", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Seat> seats;

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(LocalTime flightTime) {
        this.flightTime = flightTime;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public void addSeats(Seat seat) {
        if (seats == null)
            seats = new ArrayList<>();
        seats.add(seat);
    }

    @Override
    public String toString() {
        return "Flight [departureAirport=" + departureAirport + ", arrivalAirport=" + arrivalAirport + ", flightNumber=" + flightNumber
                + ", departureDate=" + departureDate + ", flightTime=" + flightTime + ", plane=" + plane + ", seats=" + seats + ", toString()="
                + super.toString() + "]";
    }
}
