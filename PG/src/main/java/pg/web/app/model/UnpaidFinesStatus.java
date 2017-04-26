package pg.web.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.springframework.stereotype.Component;

@Component
@Entity
@Immutable
@Table(name="unpaid_fines_status_view")
public class UnpaidFinesStatus {

	@Id
	@Column(name="id")
	int id;
	
	@Column(name="time_of_stay")
	int timeOfStay;
	
	@Column(name="amount")
	double amount;
	
	@Column(name="hourly_tax")
	double hourlyTax;

	public UnpaidFinesStatus() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTimeOfStay() {
		return timeOfStay;
	}

	public void setTimeOfStay(int timeOfStay) {
		this.timeOfStay = timeOfStay;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getHourlyTax() {
		return hourlyTax;
	}

	public void setHourlyTax(double hourlyTax) {
		this.hourlyTax = hourlyTax;
	}

}
