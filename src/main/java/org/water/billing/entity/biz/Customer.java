package org.water.billing.entity.biz;

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
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id;
	
	@Column(name="active",length=1)
	private int active;
	
	@Column(name="water_number")
	private int waterNumber;
	
	@ManyToOne()
	@JoinColumn(name="water_provider_id")
	private WaterProvider waterProvider;
	
	public WaterProvider getWaterProvider() {
		return waterProvider;
	}
	
	public void setWaterProvider(WaterProvider waterProvider) {
		this.waterProvider = waterProvider;
	}
	
	@ManyToOne()
	@JoinColumn(name="customer_type_id")
	private CustomerType customerType;
	
	public CustomerType getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "info_id")
	private CustomerInfo customerInfo;
	
	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}
	
	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="customer_charge",joinColumns={@JoinColumn(name="customer_id")},inverseJoinColumns={@JoinColumn(name="charge_id")})
	private Set<Charge> charges;
	
	@Column(name="balance")
	private float balance;
	
	
}
