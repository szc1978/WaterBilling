package org.water.billing.entity.admin;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity  
@Table(name = "pubic_announcement")
public class PublicAnnouncement {
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length=10)
	private int id = 0;
	
	@Column(name = "name", nullable = false,length = 64)
	private String name;
	
	@Column(name = "content", nullable = false,length = 4096)
	private String content;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="publisher_id")
	private SysUser publisher;
	
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "createDate", length = 32)  
	private Date createDate;
	@PrePersist
	@PreUpdate
    protected void onCreate() {
		createDate = new Date();
    }
	
	
	public PublicAnnouncement() {
		
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public SysUser getPublisher() {
		return publisher;
	}


	public void setPublisher(SysUser publisher) {
		this.publisher = publisher;
	}
}
