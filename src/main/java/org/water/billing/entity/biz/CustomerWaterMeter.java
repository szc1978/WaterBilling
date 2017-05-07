package org.water.billing.entity.biz;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.json.simple.JSONObject;
import org.water.billing.consts.Consts;

@Entity
@Table(name="customer_water_meter")
public class CustomerWaterMeter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id = 0;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="meter_type_id")
	private WaterMeterType meterType = new WaterMeterType();

	@Column(name="meter_usage")
	private String usage;

	@Column(name="status")
	private int status = Consts.WATER_METER_STATUS_USING;
	
	@Column(name="location_number")
	private String locationNumber;
	
	@Column(name="body_number",unique=true)
	private String bodyNumber;
	
	@Column(name="first_number")
	private Float firstNumber = new Float(0);
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="meter_data_id")
	private WaterMeterData waterMeterData = new WaterMeterData();
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
    @JoinTable(name="customer_meter_map",joinColumns={@JoinColumn(name="m_id")},inverseJoinColumns={@JoinColumn(name="c_id")})
	private Customer customer;
	
	@Column(name = "latest_check_time",length=64,updatable = false)
	@Temporal(TemporalType.TIMESTAMP) 
	private Date latestCheckTime; 
	
	@PrePersist
    protected void onCreate() {
		latestCheckTime = new Date();
    }
	
	public CustomerWaterMeter() {
		
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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

	public WaterMeterData getWaterMeterData() {
		return waterMeterData;
	}

	public void setWaterMeterData(WaterMeterData waterMeterData) {
		this.waterMeterData = waterMeterData;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getLatestCheckTime() {
		return latestCheckTime;
	}

	public void setLatestCheckTime(Date latestCheckTime) {
		this.latestCheckTime = latestCheckTime;
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("表位号", locationNumber);
		json.put("表身号", bodyNumber);
		json.put("状态", status);
		json.put("用途", usage);
		json.put("水表信息",meterType.toJson());
		return json;
	}
}
