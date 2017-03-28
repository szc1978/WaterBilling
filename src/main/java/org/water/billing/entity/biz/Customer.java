package org.water.billing.entity.biz;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id;
	
	@Column(name = "name",nullable=false)
	private String name;
	
	@Column(name="code")
	private String code;
	
	@Column(name="certificate_name")
	private String certificateName;
	
	@Column(name="certificate_number")
	private String certificateNumber;
	
	@Column(name="address")
	private String address;
	
	@Column(name="tel")
	private String tel;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="active",length=1)
	private int active;
	
}