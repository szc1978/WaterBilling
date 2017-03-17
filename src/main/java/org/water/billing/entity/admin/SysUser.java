package org.water.billing.entity.admin;

import java.util.Date;  
import java.util.HashSet;  
import java.util.Set;  
  
import javax.persistence.Column;  
import javax.persistence.Entity;  
import javax.persistence.FetchType;  
import javax.persistence.GeneratedValue;  
import javax.persistence.GenerationType;  
import javax.persistence.Id;  
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;  
import javax.persistence.Temporal;  
import javax.persistence.TemporalType;  
    
@Entity  
@Table(name = "sys_user")
public class SysUser {
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false,length=20)
	private Long id;
	
	@Column(name = "name", unique = true, nullable = false,length = 64)
	private String name;
	
	@Column(name = "chinese_name", length = 64)
	private String chineseName;
    
	@Column(name = "email", unique = true, nullable = false,length = 64)       
	private String email; 
         
	@Column(name = "password", length = 128)
	private String password; 
    
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "createDate", length = 32)  
	private Date createDate; 
	@PrePersist
    protected void onCreate() {
		createDate = new Date();
    }
           
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sysUser")  
	private Set<SysUserRole> SysRoles = new HashSet<SysUserRole>(0);
   
	public SysUser() {  
         
	}  
   
	public SysUser(String name, String chineseName,String email, String password, Set<SysUserRole> SysRoles) {  
		this.name = name;  
		this.chineseName = chineseName;
		this.email = email;  
		this.password = password;  
		this.SysRoles = SysRoles;  
	}   
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sysUser")
	public Set<SysUserRole> getSysRoles() { 
		return this.SysRoles; 
	}  
   
	public void setSRoles(Set<SysUserRole> SysRoles) {  
		this.SysRoles = SysRoles;  
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setSysRoles(Set<SysUserRole> sysRoles) {
		SysRoles = sysRoles;
	}
}
