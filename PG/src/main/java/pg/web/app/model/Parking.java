package pg.web.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="parkings")
public class Parking implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="number")
	private int number;
	
	@Column(name="address")
	private String address;
	
	@Column(name="hourly_tax")
	private double hourlyTax;
	
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
	
}
