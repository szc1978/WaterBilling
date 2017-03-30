package org.water.billing.entity.biz;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class CustomerInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id;
	
	@Column(name="code",nullable = false, unique = true)
	private String code;
	
	@Column(name="address")
	private String address;
	
	@Column(name="telephony")
	private String telephony;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="certificate_name",nullable = false)
	private String certificateName;
	
	@Column(name="certificate_id",nullable = false, unique = true)
	private String certificateId;
	
	@OneToOne(mappedBy = "customerInfo")
	private Customer customer;
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public CustomerInfo() {
		
	}
	
	public CustomerInfo(String code,String address,String telephony,String mobile,String certificateName,String certificateId) {
		this.code = code;
		this.address = address;
		this.telephony = telephony;
		this.mobile = mobile;
		this.certificateName = certificateName;
		this.certificateId = certificateId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephony() {
		return telephony;
	}

	public void setTelephony(String telephony) {
		this.telephony = telephony;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
}
