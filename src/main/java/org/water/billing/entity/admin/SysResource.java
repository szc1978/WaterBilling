package org.water.billing.entity.admin;

import javax.persistence.Column;  
import javax.persistence.Entity;  
import javax.persistence.GeneratedValue;  
import javax.persistence.GenerationType;  
import javax.persistence.Id;  
import javax.persistence.Table;  

@Entity  
@Table(name="sys_resource")  
public class SysResource {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
	@Column (name="id",length=20)  
	private int id; 
	
	@Column(name="resource_string",unique = true, nullable = false,length=128)
	private String resourceString;
	
	@Column(name="resource_id",unique = true, nullable = false,length=128) 
	private String resourceId;
	
	@Column(name="memo",length=256) 
	private String memo;  
          
	@Column(name="name",unique = true, nullable = false,length=128) 
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResourceString() {
		return resourceString;
	}

	public void setResourceString(String resourceString) {
		this.resourceString = resourceString;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
          
}  