package org.water.billing.entity.admin;

import java.util.Date;

import javax.persistence.Column;  
import javax.persistence.Entity;  
import javax.persistence.FetchType;  
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
@Table(name="sys_user_role")  
public class SysUserRole {  
    @Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="id",length=20)  
    private int id;  
      
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "uid")  
    private SysUser sysUser;
      
    @Column(name="name",unique = true, nullable = false,length=100)  
    private String name;
    
    @Column(name="rid",nullable = false,length=20)
    private int rid;
    
    @Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "createDate", length = 32)  
	private Date createDate; 
	@PrePersist
    protected void onCreate() {
		createDate = new Date();
    }
    
    public SysUserRole() {
    	
    }
    
    public SysUserRole(String name,SysUser sysUser,int rid) {
    	this.name = name;
    	this.sysUser = sysUser;
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
    
    public SysUser getSUser() {  
        return sysUser;  
    }  
    public void setSysUser(SysUser sysUser) {  
    	this.sysUser = sysUser;  
    }

	public SysUser getSysUser() {
		return sysUser;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}
    
	 
}  
