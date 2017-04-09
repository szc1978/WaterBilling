package org.water.billing.entity.biz;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="customer_water")
public class CustomerWater {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id = 0;
	
	@Column(name="org_number")
	private Float orgNumber = new Float(0);
	
	@Column(name="new_number")
	private Float newNumber = new Float(0);
	
	@Column(name="inputer_id")
	private int inputerId;
	
	@OneToOne(mappedBy = "customerWater")
	private Customer customer;
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	@Column(name = "input_date",length=64,updatable = false)
	@Temporal(TemporalType.TIMESTAMP) 
	private Date inputDate; 
	@PrePersist
    protected void onCreate() {
		inputDate = new Date();
    }
	
	public CustomerWater() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Float getOrgNumber() {
		return orgNumber;
	}

	public void setOrgNumber(Float orgNumber) {
		this.orgNumber = orgNumber;
	}

	public Float getNewNumber() {
		return newNumber;
	}

	public void setNewNumber(Float newNumber) {
		this.newNumber = newNumber;
	}

	public int getInputerId() {
		return inputerId;
	}

	public void setInputerId(int inputerId) {
		this.inputerId = inputerId;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
}
