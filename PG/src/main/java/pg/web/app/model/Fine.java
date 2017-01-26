package pg.web.app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="fines")
public class Fine implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="vehicle_reg_num")
	private String vehicleRegNum;
	
	@Column(name="kind_of_violation")
	private String kindOfViolation;
	
	@Column(name="parking_number")
	private int parkingNumber;
	
	@Column(name="description")
	private String description;
	
	@OneToOne(optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name="payment_info_id")
	private FinePaymentInfo finePaymentInfo;
	
	@Column(name="paid")
	private boolean paid;
	
	@OneToOne()
    @JoinColumn(name="id",nullable=true)
	private UnpaidFinesStatus unpaidFinesStatus;
	
	public Fine() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVehicleRegNum() {
		return vehicleRegNum;
	}

	public void setVehicleRegNum(String vehicleRegNum) {
		this.vehicleRegNum = vehicleRegNum;
	}

	public String getKindOfViolation() {
		return kindOfViolation;
	}

	public void setKindOfViolation(String kindOfViolation) {
		this.kindOfViolation = kindOfViolation;
	}

	public int getParkingNumber() {
		return parkingNumber;
	}

	public void setParkingNumber(int parkingNumber) {
		this.parkingNumber = parkingNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public Date getDate() {
		return date;
	}

	public FinePaymentInfo getFinePaymentInfo() {
		return finePaymentInfo;
	}

	public void setFinePaymentInfo(FinePaymentInfo finePaymentInfo) {
		this.finePaymentInfo = finePaymentInfo;
	}

	public UnpaidFinesStatus getUnpaidFinesStatus() {
		return unpaidFinesStatus;
	}

	public void setUnpaidFinesStatus(UnpaidFinesStatus unpaidFinesStatus) {
		this.unpaidFinesStatus = unpaidFinesStatus;
	}
	
}
