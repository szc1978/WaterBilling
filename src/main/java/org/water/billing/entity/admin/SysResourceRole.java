package org.water.billing.entity.admin;

import java.util.Date;  

import javax.persistence.Column;  
import javax.persistence.Entity;  
import javax.persistence.GeneratedValue;  
import javax.persistence.GenerationType;  
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;  

@Entity  
@Table(name="sys_resource_role")  
public class SysResourceRole { 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column (name="id",length=20)
	private int id; 
	
	@Column(name="name",unique = true, nullable = false,length=128)
	private String name;
	
	@Column(name="resource_string",length=128) 
	private String resourceString;
	
	@Column(name="rid",length=20)
	private int rid;
	
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "createDate", length = 32)  
	private Date createDate; 
	@PrePersist
    protected void onCreate() {
		createDate = new Date();
    }
	
	public SysResourceRole() {
		
	}
	
	public SysResourceRole(String name,String resourceString,int rid) {
		this.name = name;
		this.resourceString = resourceString;
		this.rid = rid;
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

	public String getResourceString() {
		return resourceString;
	}

	public void setResourceString(String resourceString) {
		this.resourceString = resourceString;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

  
            
}  
