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
@Table(name="water_meter")
public class WaterMeter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id = 0;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="vender_id")
	private WaterMeterConfig vender;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="model_id")
	private WaterMeterConfig model;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="size_id")
	private WaterMeterConfig size;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="usage_id")
	private WaterMeterConfig usage;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="status_id")
	private WaterMeterConfig status;
	
	@Column(name="location_number")
	private String locationNumber;
	
	@Column(name="body_number")
	private String bodyNumber;
	
	@Column(name="first_number")
	private String firstNumber;
	
	@Column(name = "status_time",length=64,updatable = false)
	@Temporal(TemporalType.TIMESTAMP) 
	private Date statusTime; 
	@PrePersist
    protected void onCreate() {
		statusTime = new Date();
    }
	
	public WaterMeter() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public WaterMeterConfig getVender() {
		return vender;
	}

	public void setVender(WaterMeterConfig vender) {
		this.vender = vender;
	}

	public WaterMeterConfig getModel() {
		return model;
	}

	public void setModel(WaterMeterConfig model) {
		this.model = model;
	}

	public WaterMeterConfig getSize() {
		return size;
	}

	public void setSize(WaterMeterConfig size) {
		this.size = size;
	}

	public WaterMeterConfig getUsage() {
		return usage;
	}

	public void setUsage(WaterMeterConfig usage) {
		this.usage = usage;
	}

	public WaterMeterConfig getStatus() {
		return status;
	}

	public void setStatus(WaterMeterConfig status) {
		this.status = status;
	}
	
}
