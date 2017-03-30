package org.water.billing.entity.biz;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Column(name="active",length=1)
	private int active = 1;
	
	@Column(name="water_number")
	private int waterNumber;
	
	@Column(name="balance")
	private float balance;
	
	@ManyToOne()
	@JoinColumn(name="water_provider_id")
	private WaterProvider waterProvider;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="customer_type_id")
	private CustomerType customerType;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "info_id")
	private CustomerInfo customerInfo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="customer_charge",joinColumns={@JoinColumn(name="customer_id")},inverseJoinColumns={@JoinColumn(name="charge_id")})
	private Set<Charge> charges;
	
	@Column(name = "create_time",length=64)
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createTime; 
	@PrePersist
    protected void onCreate() {
		createTime = new Date();
    }
	
	public Customer() {
		
	}
	
	public Customer(String name) {
		this.name = name;
	}
	
	public WaterProvider getWaterProvider() {
		return waterProvider;
	}
	
	public void setWaterProvider(WaterProvider waterProvider) {
		this.waterProvider = waterProvider;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	
	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}
	
	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getWaterNumber() {
		return waterNumber;
	}

	public void setWaterNumber(int waterNumber) {
		this.waterNumber = waterNumber;
	}

	public Set<Charge> getCharges() {
		return charges;
	}

	public void setCharges(Set<Charge> charges) {
		this.charges = charges;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
