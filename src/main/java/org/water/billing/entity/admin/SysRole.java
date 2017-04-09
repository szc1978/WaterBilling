package org.water.billing.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.water.billing.consts.Consts;

@Entity
@Table(name="sys_role")
public class SysRole {
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length=10)
	private Integer id = 0;
	
	@Column(name="name",unique = true, nullable = false,length=100)  
    private String name;
    
    @Column(name="code",length=32)
    private String code;
    
    @Column(name="type",length=128)
    private String type;
    
    @Column(name="memo",length=128)
    private String memo;
    
    @Column(name="active",length=1)
    private int active = Consts.STATUS_DEFINE_ACTIVE;
    
    public SysRole() {
    	
    }
    
    public SysRole(String name,String type,String code,String memo) {
    	this.name = name;
    	this.type = type;
    	this.code = code;
    	this.memo = memo;
    }
    
    public SysRole(Integer id,String name,String type,String code,String memo) {
    	this.id = id;
    	this.name = name;
    	this.type = type;
    	this.code = code;
    	this.memo = memo;
    }
    
    public int hashCode() {
		return new Integer(id).hashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if (! (obj instanceof SysRole)) {
            return false;
        }
        return this.id == ((SysRole)obj).getId();
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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
}
