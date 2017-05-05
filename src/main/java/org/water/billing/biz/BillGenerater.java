package org.water.billing.biz;

import java.util.Date;
import java.util.List;

import org.water.billing.consts.ChargeTypeEnum;
import org.water.billing.consts.Consts;
import org.water.billing.entity.biz.Bill;
import org.water.billing.entity.biz.Charge;
import org.water.billing.entity.biz.ChargeParameter;
import org.water.billing.entity.biz.Customer;
import org.water.billing.entity.biz.CustomerType;
import org.water.billing.entity.biz.CustomerWaterMeter;
import org.water.billing.entity.biz.WaterMeterData;

public class BillGenerater {
	private CustomerWaterMeter customerWaterMeter = null;
	private Customer customer = null;
	private WaterMeterData waterMeterData = null;
	private Float totalPostage = new Float(0);
	private String detailedBill = "";
	private Float totalWaterNumber;
	private int billType;
	
	public BillGenerater(CustomerWaterMeter customerWaterMeter,int billType) {
		this.customerWaterMeter = customerWaterMeter;
		waterMeterData = customerWaterMeter.getWaterMeterData();
		this.customer = customerWaterMeter.getCustomer();
		this.billType = billType;
		totalWaterNumber = waterMeterData.getNewNumber() - waterMeterData.getOrgNumber();
	}
	
	public BillGenerater(Customer customer,int billType) {
		this.customer = customer;
		this.billType = billType;
	}
	
	public Bill genBill4DedivatedCharge(String billName,List<Charge> charges) {
		Bill bill = new Bill();
		bill.setBillType(billType);
		bill.setName(billName);
		calPostage4PriceByDedicated(charges);
		bill.setTotalPostage(totalPostage);
		bill.setDetailContent(detailedBill);
		bill.setCustomerCode(customer.getCustomerInfo().getCode());
		//bill.setWaterMeterBodyNumber(customerWaterMeter.getBodyNumber());;
		return bill;
	}
	
	public Bill genBill() {
		calPostage4PriceByBottom(); //must cal this item first
		calPostage4PriceByNumber();
		calPostage4PriceByStep();
		calPostage4PriceByDedicated();
		Bill bill = new Bill();
		bill.setBillType(billType);
		bill.setBeginWaterWord(waterMeterData.getOrgNumber());
		bill.setEndWaterWord(waterMeterData.getNewNumber());
		bill.setTotalPostage(totalPostage);
		bill.setDetailContent(detailedBill);
		bill.setName(customerWaterMeter.getBodyNumber() + waterMeterData.getPayMonth() + "月账单");
		bill.setInputDate(new Date());
		bill.setInputerName(waterMeterData.getInputerName());
		bill.setCustomerCode(customer.getCustomerInfo().getCode());
		bill.setWaterMeterBodyNumber(customerWaterMeter.getBodyNumber());;
		if(customer.getBalance() >= totalPostage)
			bill.setAutoChargeFlag(Consts.BILL_AUTO_CHARGE_FLAG);
		return bill;
	}
	
	private void calPostage4PriceByBottom() {
		CustomerType customerType = customer.getCustomerType();
		for(Charge charge : customerType.getCharges()) {
			if(charge.getChargeType() != ChargeTypeEnum.CHARGE_BY_BOTTOM.getId()) 
				continue;
			Float postage = charge.getChargeParameter().getBottomPrice() * charge.getChargeParameter().getBottomNumber();
			if(totalWaterNumber > charge.getChargeParameter().getBottomNumber()) {
				totalWaterNumber -= charge.getChargeParameter().getBottomNumber();
			} else {
				totalWaterNumber = new Float(0);
			}
			totalPostage += postage;
			detailedBill += charge.getName() + ":" + postage;
			break;
		}
	}
	
	private void calPostage4PriceByNumber() {
		CustomerType customerType = customer.getCustomerType();
		for(Charge charge : customerType.getCharges()) {
			if(charge.getChargeType() != ChargeTypeEnum.CHARGE_BY_PER_PRICE.getId()) 
				continue;
			Float postage = charge.getChargeParameter().getPricePerNumber() * totalWaterNumber;
			totalPostage += postage;
			detailedBill += ";" + charge.getName() + ":" + postage;
		}
	}
	
	private void calPostage4PriceByDedicated() {
		CustomerType customerType = customer.getCustomerType();
		for(Charge charge : customerType.getCharges()) {
			if(charge.getChargeType() != ChargeTypeEnum.CHARGE_BY_DEDICATE_PRICE.getId()) 
				continue;
			totalPostage +=  charge.getChargeParameter().getPriceByDedicated();
			detailedBill += ";" + charge.getName() + ":" +  charge.getChargeParameter().getPriceByDedicated();
		}
	}
	
	private void calPostage4PriceByDedicated(List<Charge> charges) {
		for(Charge charge : charges) {
			totalPostage +=  charge.getChargeParameter().getPriceByDedicated();
			detailedBill += ";" + charge.getName() + ":" +  charge.getChargeParameter().getPriceByDedicated();
		}
	}
	
	private void calPostage4PriceByStep() {
		CustomerType customerType = customer.getCustomerType();
		for(Charge charge : customerType.getCharges()) {
			if(charge.getChargeType() != ChargeTypeEnum.CHARGE_STEP_BY_STEP.getId())
				continue;
			
			Float p1 = calPostage4Step(charge,waterMeterData.getYearCount() + totalWaterNumber);
			Float p2 = calPostage4Step(charge,waterMeterData.getYearCount());
			
			totalPostage += (p1 - p2);
			detailedBill += ";" + charge.getName() + ":" + (p1-p2);
			break;
		}
	}
	
	private Float calPostage4Step(Charge charge,Float waterNumber) {
		ChargeParameter cp = charge.getChargeParameter();
		Float postage = new Float(0);
		if(waterNumber < cp.getStep2Number())
			postage += waterNumber * cp.getStep1Price();
		if(waterNumber > cp.getStep2Number() && waterNumber < cp.getStep3Number())
			postage += (waterNumber - cp.getStep2Number()) * cp.getStep2Price();
		if(waterNumber > cp.getStep3Number() ) {
			postage += (cp.getStep3Number() - cp.getStep2Number()) * cp.getStep2Price();
			postage += (waterNumber - cp.getStep3Number()) * cp.getStep3Price();
		}
		return postage;
	}
}
