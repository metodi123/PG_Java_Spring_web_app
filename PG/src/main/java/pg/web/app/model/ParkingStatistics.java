package pg.web.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="parking_statistics")
public class ParkingStatistics implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="parking_number")
	private int parkingNumber;
	
	@Column(name="current_vehicle_count")
	private int currentVehicleCount;
	
	@Column(name="gainings")
	private double gainings;
	
	@Column(name="paid_fines_count")
	private int paidFinesCount;
	
	@Column(name="year")
	private int year;
	
	@Column(name="month")
	private String month;
	
	public ParkingStatistics() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParkingNumber() {
		return parkingNumber;
	}

	public void setParkingNumber(int parkingNumber) {
		this.parkingNumber = parkingNumber;
	}

	public int getCurrentVehicleCount() {
		return currentVehicleCount;
	}

	public void setCurrentVehicleCount(int currentVehicleCount) {
		this.currentVehicleCount = currentVehicleCount;
	}

	public double getGainings() {
		return gainings;
	}

	public void setGainings(double gainings) {
		this.gainings = gainings;
	}

	public int getPaidFinesCount() {
		return paidFinesCount;
	}

	public void setPaidFinesCount(int paidFinesCount) {
		this.paidFinesCount = paidFinesCount;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
}
