package org.water.billing.entity.admin;

import java.util.Date;
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
import javax.persistence.Temporal;  
import javax.persistence.TemporalType;

import org.water.billing.consts.Consts;  
    
@Entity  
@Table(name = "sys_user")
public class SysUser {
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length=10)
	private int id = 0;
	
	@Column(name = "name", unique = true, nullable = false,length = 64)
	private String name;
	
	@Column(name = "chinese_name", length = 64)
	private String chineseName;
    
	@Column(name = "email", length = 64)       
	private String email; 
         
	@Column(name = "password", length = 128)
	private String password; 
	
	@Column(name = "active",length = 1)
	private int active = Consts.STATUS_DEFINE_ACTIVE;
    
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "createDate", length = 32)  
	private Date createDate; 
           
	@ManyToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    @JoinTable(name="user_role",joinColumns={@JoinColumn(name="u_id")},inverseJoinColumns={@JoinColumn(name="r_id")})
    private Set<SysRole> sysRoles;
   
	public SysUser() {  
		createDate = new Date();
	}  
   
	public SysUser(String name, String chineseName,String email, String password, Set<SysRole> sysRoles) {  
		this.name = name;  
		this.chineseName = chineseName;
		this.email = email;  
		this.password = password;  
		this.sysRoles = sysRoles; 
		createDate = new Date();
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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Set<SysRole> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}


}
