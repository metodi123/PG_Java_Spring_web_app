package pg.web.app.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="parkings")
public class Parking implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="number")
	private int number;
	
	@Column(name="address")
	private String address;
	
	@Column(name="hourly_tax")
	private double hourlyTax;
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="location_id", nullable=false)
	private Location location;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="parking_number", nullable=false, insertable=false, updatable=false)
	private Set<ParkingStatistics> parkingStatistics = new HashSet<ParkingStatistics>();
	
	public Parking() {
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getHourlyTax() {
		return hourlyTax;
	}

	public void setHourlyTax(double hourlyTax) {
		this.hourlyTax = hourlyTax;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Set<ParkingStatistics> getParkingStatistics() {
		return parkingStatistics;
	}

	public void setParkingStatistics(Set<ParkingStatistics> parkingStatistics) {
		this.parkingStatistics = parkingStatistics;
	}
	
}
