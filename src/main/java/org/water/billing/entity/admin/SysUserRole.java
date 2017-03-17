package org.water.billing.entity.admin;

import javax.persistence.Column;  
import javax.persistence.Entity;  
import javax.persistence.FetchType;  
import javax.persistence.GeneratedValue;  
import javax.persistence.GenerationType;  
import javax.persistence.Id;  
import javax.persistence.JoinColumn;  
import javax.persistence.ManyToOne;  
import javax.persistence.Table;  
 
@Entity  
@Table(name="sys_user_role")  
public class SysUserRole {  
    @Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="id",length=20)  
    private int id;  
      
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "uid", nullable = false)  
    private SysUser sysUser;
      
    @Column(name="name",length=100)  
    private String name;
    
    @Column(name="role_code",length=32)
    private String roleCode;
    
    public SysUserRole() {
    	
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
    
    public SysUser getSUser() {  
        return sysUser;  
    }  
    public void setSysUser(SysUser sysUser) {  
    	this.sysUser = sysUser;  
    }

	public SysUser getSysUser() {
		return sysUser;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
    
	 
}  
