package org.water.billing.entity.biz;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer_charge")
public class CustomerCharge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",length=10)
	private int id;
	
	@Column(name="customer_id")
	private int customerId;
	
	@Column(name="need_charge",length=1)
	private int needCharge;
	
	@Column(name="water_number",length=10)
	private int waterNumber;
	
	@Column(name="firstDate")
	private Date firstDate;
	
	@Column(name="latestCharge")
	private Date latestCharge;
}
