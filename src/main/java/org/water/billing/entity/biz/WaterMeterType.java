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
@Table(name="water_meter_type")
public class WaterMeterType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id = 0;
	
	@Column(name="name",unique = true)
	private String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="producer_id")
	private WaterMeterConfig producer;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="model_id")
	private WaterMeterConfig model;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="size_id")
	private WaterMeterConfig size;
	
	public WaterMeterType() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WaterMeterConfig getProducer() {
		return producer;
	}

	public void setProducer(WaterMeterConfig producer) {
		this.producer = producer;
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
}
