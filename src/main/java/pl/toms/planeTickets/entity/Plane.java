package pl.toms.planeTickets.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="plane_type")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property="@planeId")
public class Plane extends BaseEntity{

	@Column(name="brand")
	@NotBlank
	private String brand;
	
	@Column(name="model")
	@NotBlank
	private String model;
	
	@Column(name="seats_rows")
	@NotNull
	private int seatsRows;

	@Column(name="number_of_premium_rows")
	private int premiumRows;
	
	@Column(name="number_of_seats_in_row")
	@NotNull
	private int seatsInRow;
	
	/**
	 * Loty z tym modelem.
	 */
	@OneToMany(mappedBy = "plane")
	private List<Flight> flights;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getSeatsRows() {
		return seatsRows;
	}

	public void setSeatsRows(int seatsRows) {
		this.seatsRows = seatsRows;
	}

	public int getPremiumRows() {
		return premiumRows;
	}

	public void setPremiumRows(int premiumRows) {
		this.premiumRows = premiumRows;
	}

	public int getSeatsInRow() {
		return seatsInRow;
	}

	public void setSeatsInRow(int seatsInRow) {
		this.seatsInRow = seatsInRow;
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	
}
