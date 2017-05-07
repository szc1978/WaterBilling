package org.water.billing.entity.biz;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="customer_pay_history")
public class CustomerPayHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id;
	
	@Column(name="pay_number")
	private Float payNumber;
	
	@Column(name="customer_balance")
	private Float customerBalance;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@Column(name = "create_time",length=64,updatable = false)
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createTime; 
	
	@PrePersist
    protected void onCreate() {
		createTime = new Date();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Float getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(Float payNumber) {
		this.payNumber = payNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getCustomerBalance() {
		return customerBalance;
	}

	public void setCustomerBalance(Float customerBalance) {
		this.customerBalance = customerBalance;
	}
}
