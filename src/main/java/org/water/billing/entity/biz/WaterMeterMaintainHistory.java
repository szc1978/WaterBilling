package org.water.billing.entity.biz;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="water_meter_maintain_history")
public class WaterMeterMaintainHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id = 0;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="water_meter_id")
	private CustomerWaterMeter waterMeter;
	
	@Column(name="action")
	private int action;
	
	@Column(name="reason")
	private String reason;
	
	@Column(name = "create_time",length=64,updatable = false)
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createTime; 
	
	@PrePersist
    protected void onCreate() {
		createTime = new Date();
    }
	
	public WaterMeterMaintainHistory() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public CustomerWaterMeter getWaterMeter() {
		return waterMeter;
	}

	public void setWaterMeter(CustomerWaterMeter waterMeter) {
		this.waterMeter = waterMeter;
	}
}
