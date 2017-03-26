package org.water.billing.entity.admin;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="sys_resource")
public class SysResource {
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length=10)
	private int id = 0;
	
	@Column(name="name",unique = true, nullable = false,length = 64)
	private String name;
	
	@Column(name="resource_string",unique=true,nullable=false,length=128)
	private String resourceString;
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    @JoinTable(name="resource_role",joinColumns={@JoinColumn(name="resource_id")},inverseJoinColumns={@JoinColumn(name="role_id")})
    private Set<SysRole> sysRoles;
	
	public SysResource(){
		
	}
	
	public SysResource(String name,String resourceString,Set<SysRole> sysRoles) {
		this.name = name;
		this.resourceString = resourceString;
		this.sysRoles = sysRoles;
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

	public Set<SysRole> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}
	
	
}
