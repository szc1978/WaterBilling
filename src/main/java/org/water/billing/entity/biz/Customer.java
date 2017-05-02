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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.water.billing.consts.Consts;

@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id = 0;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Column(name="balance")
	private float balance = new Float(0.00);
	
	@Column(name="read_meter_cycle")
	private String readMeterCycle;
	
	/*4 bit, pending|accountstatus
	 * 0 : inactive
	 * 1 : active
	 * 2 : active -> inactive, pending for approve
	 * 3 : inactive -> active, pending for approve
	 */
	@Column(name="staus",length=1)
	private int status = Consts.CUSTOMER_STATUS_ACTIVE_BIT | Consts.CUSTOMER_STATUS_PENDING_BIT;
	
	@ManyToOne()
	@JoinColumn(name="water_provider_id")
	private WaterProvider waterProvider = new WaterProvider();
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="customer_type_id")
	private CustomerType customerType = new CustomerType();
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "info_id")
	private CustomerInfo customerInfo = new CustomerInfo();
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
    @JoinTable(name="customer_meter_map",joinColumns={@JoinColumn(name="c_id")},inverseJoinColumns={@JoinColumn(name="m_id")})
	private Set<CustomerWaterMeter> meters;
	
	@Column(name = "create_time",length=64,updatable = false)
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

	/*public float getWaterNumber() {
		return waterNumber;
	}

	public void setWaterNumber(float waterNumber) {
		this.waterNumber = waterNumber;
	}*/

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

	public String getReadMeterCycle() {
		return readMeterCycle;
	}

	public void setReadMeterCycle(String readMeterCycle) {
		this.readMeterCycle = readMeterCycle;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<CustomerWaterMeter> getMeters() {
		return meters;
	}

	public void setMeters(Set<CustomerWaterMeter> meters) {
		this.meters = meters;
	}
	
}
