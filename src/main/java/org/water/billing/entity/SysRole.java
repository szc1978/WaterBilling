package org.water.billing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_role")
public class SysRole {
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false,length=20)
	private Integer id;
	
	@Column(name="name",unique = true, nullable = false,length=100)  
    private String name;
    
    @Column(name="code",length=32)
    private String code;
    
    @Column(name="type",length=128)
    private String type;
    
    @Column(name="memo",length=128)
    private String memo;
    
    public SysRole() {
    	
    }
    
    public SysRole(String name,String type,String code,String memo) {
    	this.name = name;
    	this.type = type;
    	this.code = code;
    	this.memo = memo;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
