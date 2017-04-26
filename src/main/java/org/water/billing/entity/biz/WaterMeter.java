package org.water.billing.entity.biz;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="water_meter")
public class WaterMeter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id = 0;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="meter_type_id")
	private WaterMeterType meterType;

	@Column(name="meter_usage")
	private String usage;

	@Column(name="status")
	private String status;
	
	@Column(name="location_number")
	private String locationNumber;
	
	@Column(name="body_number")
	private String bodyNumber;
	
	@Column(name="first_number")
	private Float firstNumber;
	
	public WaterMeter() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public WaterMeterType getMeterType() {
		return meterType;
	}

	public void setMeterType(WaterMeterType meterType) {
		this.meterType = meterType;
	}

	public String getLocationNumber() {
		return locationNumber;
	}

	public void setLocationNumber(String locationNumber) {
		this.locationNumber = locationNumber;
	}

	public String getBodyNumber() {
		return bodyNumber;
	}

	public void setBodyNumber(String bodyNumber) {
		this.bodyNumber = bodyNumber;
	}

	public Float getFirstNumber() {
		return firstNumber;
	}

	public void setFirstNumber(Float firstNumber) {
		this.firstNumber = firstNumber;
	}

	
}
