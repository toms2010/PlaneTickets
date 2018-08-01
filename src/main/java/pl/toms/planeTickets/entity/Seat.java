package pl.toms.planeTickets.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="seat")
public class Seat extends BaseEntity{
	/**
	 * Statusy miejsc
	 */
	public enum SeatStatus{
		/** Free */
		F("F", "Free"),
		/** Reserved */
		R("R", "Reserved"),
		/** Not avable */
		N("N", "Not avable");
		
		String status;
		String name;
		
		private SeatStatus(String status, String name) {
			this.status = status;
			this.name = name;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		
	}

	/**
	 * Numer miejsca
	 */
	@Column(name="number")
	@NotNull
	private int number;
	
	//TODO passager id.
	//TODO priceGroup id.
	
	/**
	 * Identyfikator lotu.
	 */
	@ManyToOne 
	@JoinColumn(name = "fly_id", nullable = false)
	@NotEmpty
	private Flight flight;
	
	/**
	 * Status miejsca
	 */
	@Column(name="status")
	@NotBlank
	private String status;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}