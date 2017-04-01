package org.water.billing.entity.biz;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="charge_parameters")
public class ChargeParameter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id;
	
	@Column(name="price_per_number")
	private Float pricePerNumber = new Float(0.00);
	
	@Column(name="price_by_dedicated")
	private Float priceByDedicated = new Float(0.00);
	
	@Column(name="step1_number")
	private Float step1Number = new Float(0.00);
	
	@Column(name="step1_price")
	private Float step1Price = new Float(0.00);
	
	@Column(name="step2_number")
	private Float step2Number = new Float(0.00);
	
	@Column(name="step2_price")
	private Float step2Price = new Float(0.00);
	
	@Column(name="step3_number")
	private Float step3Number = new Float(0.00);
	
	@Column(name="step3_price")
	private Float step3Price = new Float(0.00);
	
	@OneToOne(mappedBy = "chargeParameter")
	private Charge charge;
	
	public ChargeParameter() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Float getPricePerNumber() {
		return pricePerNumber;
	}

	public void setPricePerNumber(Float pricePerNumber) {
		this.pricePerNumber = pricePerNumber;
	}

	public Float getPriceByDedicated() {
		return priceByDedicated;
	}

	public void setPriceByDedicated(Float priceByDedicated) {
		this.priceByDedicated = priceByDedicated;
	}

	public Float getStep1Number() {
		return step1Number;
	}

	public void setStep1Number(Float step1Number) {
		this.step1Number = step1Number;
	}

	public Float getStep1Price() {
		return step1Price;
	}

	public void setStep1Price(Float step1Price) {
		this.step1Price = step1Price;
	}

	public Float getStep2Number() {
		return step2Number;
	}

	public void setStep2Number(Float step2Number) {
		this.step2Number = step2Number;
	}

	public Float getStep2Price() {
		return step2Price;
	}

	public void setStep2Price(Float step2Price) {
		this.step2Price = step2Price;
	}

	public Float getStep3Number() {
		return step3Number;
	}

	public void setStep3Number(Float step3Number) {
		this.step3Number = step3Number;
	}

	public Float getStep3Price() {
		return step3Price;
	}

	public void setStep3Price(Float step3Price) {
		this.step3Price = step3Price;
	}
}
