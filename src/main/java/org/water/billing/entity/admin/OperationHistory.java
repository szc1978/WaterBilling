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
@Table(name="operation_log")
public class OperationHistory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column (name="id",length=20)
	private long id; 
	
	@Column(name = "function_name",length=64)
	private String functionName;
	
	@Column(name = "user_name",length=64)
	private String userName;
	
	@Column(name = "ip",length=64)
	private String ip;
	
	@Column(name = "content",length=64)
	private String content;
	
	@Column(name = "type",length=64)
	private String type;
	
	@Column(name = "login_time",length=64)
	@Temporal(TemporalType.TIMESTAMP) 
	private Date loginTime; 
	@PrePersist
    protected void onCreate() {
		loginTime = new Date();
    }
	
	public OperationHistory() {
		
	}
	
	public OperationHistory(String functionName,String userName,String ip,String content,String type) {
		this.userName = userName;
		this.functionName = functionName;
		this.ip = ip;
		this.content = content;
		this.type = type;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
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

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
