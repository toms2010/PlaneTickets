package pl.toms.planeTickets.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

@Entity
@Table(name = "seat")
public class Seat extends BaseEntity {
    /**
     * Statusy miejsc
     */
    public enum SeatStatus {
        /** Free */
        F("F", "Free"),
        /** Reserved */
        R("R", "Reserved"),
        /** Not avable */
        N("N", "Not avable");

        private String status;
        private String name;

        private SeatStatus(String status, String name)
        {
            this.status = status;
            this.name = name;
        }

        public String getStatus()
        {
            return status;
        }

        public String getName()
        {
            return name;
        }
    }

    /**
     * Numer miejsca
     */
    @NotNull
    @Column(name = "number")
    private int number;

    /**
     * Identyfikator lotu.
     */
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    /**
     * Status miejsca
     */
    @Size(min=1, max=1)
    @Column(name = "status")
    private String status;

    /**
     * Nazwa pasażera (tymczasowo bo nie istnieje jeszcze encja pasażera)
     */
    @Column(name = "passager_temp")
    private String passagerName;
    
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

    public String getPassagerName()
    {
        return passagerName;
    }

    public void setPassagerName(String passagerName)
    {
        this.passagerName = passagerName;
    }

    @Override
    public String toString()
    {
        return "Seat [number=" + number + ", flight=" + flight + ", status=" + status + ", passagerName=" + passagerName + ", toString()=" + super.toString()
            + "]";
    }
}