package org.water.billing.entity;

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
@Table(name="login_log")
public class LoginLog {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column (name="id",length=20)
	private int id; 
	
	@Column(name = "user_name",length=64)
	private String userName;
	
	@Column(name = "ip",length=64)
	private String ip;
	
	@Column(name = "login_time",length=64)
	@Temporal(TemporalType.TIMESTAMP) 
	private Date loginTime; 
	@PrePersist
    protected void onCreate() {
		loginTime = new Date();
    }
	
	public LoginLog() {
		
	}
	
	public LoginLog(String userName,String ip) {
		this.userName = userName;
		this.ip = ip;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
}
