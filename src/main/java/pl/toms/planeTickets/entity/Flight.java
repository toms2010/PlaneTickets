package pl.toms.planeTickets.entity;

import java.util.Date;
import java.util.List;

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

@Entity
@Table(name = "fly")
public class Flight extends BaseEntity{
	/**
	 * 3 literowy kod lotniska.
	 */
	@Column(name = "departure_airport")
	@Size(min=3, max=3)
	private String departureAirport;

	/**
	 * 3 literowy kod lotniska.
	 */
	@Column(name = "arrival_airport")
	@Size(min=3, max=3)
	private String arrivalAirport;

	/**
	 * Numer lotu
	 */
	@Column(name="fly_number")
	@NotEmpty
	private String flightNumber;
	
	/**
	 * Data wylotu. //TODO
	 */
	@Column(name = "departure_date")
//	@NotNull
	private Date departureDate;

	/**
	 * Czas lotu. //TODO
	 */
	@Column(name = "flight_time")
//	@NotNull
	private Date flightTime;

	/** 
	 * Samolot.
	 */
	@ManyToOne 
	@JoinColumn(name = "plane_type_id", nullable = false)
//	@NotNull
	private Plane plane;
	
	/**
	 * Miejsca w samolocie podczas lotu.
	 */
	@OneToMany(mappedBy = "flight", fetch = FetchType.EAGER)
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

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(Date flightTime) {
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
	
}
