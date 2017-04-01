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
	
	@Column(name = "module_name",length=64)
	private String moduleName;
	
	@Column(name = "op_name",length=64)
	private String opName;
	
	@Column(name = "user_chinese_name",length=64)
	private String userChineseName;
	
	@Column(name = "ip",length=64)
	private String ip;
	
	@Column(name = "content",length=64)
	private String content;
	
	//map OpTypeEnum
	@Column(name = "type",length=1)
	private int type;
	
	@Column(name = "op_time",length=64)
	@Temporal(TemporalType.TIMESTAMP) 
	private Date opTime; 
	@PrePersist
    protected void onCreate() {
		opTime = new Date();
    }
	
	public OperationHistory() {
		
	}
	
	public OperationHistory(String userChineseName,String moduleName,String opName,String ip,String content,int type) {
		this.userChineseName = userChineseName;
		this.opName = opName;
		this.moduleName = moduleName;
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

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getUserChineseName() {
		return userChineseName;
	}

	public void setUserChineseName(String userChineseName) {
		this.userChineseName = userChineseName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	
	

}
