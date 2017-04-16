package org.water.billing.entity.biz;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="water_meter_config")
public class WaterMeterConfig {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id = 0;
	
	@Column(name="config_item_id")
	private int configItemId = 0;
	
	@Column(name="config_item_value")
	private String configItemValue;
	
	public WaterMeterConfig() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getConfigItemId() {
		return configItemId;
	}

	public void setConfigItemId(int configItemId) {
		this.configItemId = configItemId;
	}

	public String getConfigItemValue() {
		return configItemValue;
	}

	public void setConfigItemValue(String configItemValue) {
		this.configItemValue = configItemValue;
	}
	
	
}
