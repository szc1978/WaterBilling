package org.water.billing.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="login_log")
public class SysConfigurationItem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column (name="id",length=20)
	private long id; 
	
	@Column(name="item")
	private String item;
	
	@Column(name="value")
	private String value;
	
	public SysConfigurationItem() {
		
	}
	
	public SysConfigurationItem(String item,String value) {
		this.item = item;
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
