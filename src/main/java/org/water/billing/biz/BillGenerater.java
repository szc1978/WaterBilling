package org.water.billing.biz;

import java.util.Date;

import org.water.billing.consts.ChargeTypeEnum;
import org.water.billing.consts.Consts;
import org.water.billing.entity.biz.Bill;
import org.water.billing.entity.biz.Charge;
import org.water.billing.entity.biz.Customer;
import org.water.billing.entity.biz.CustomerType;

public class BillGenerater {
	private Customer customer = null;
	private Float totalPostage = new Float(0);
	private String detailedBill = "";
	private Float totalWaterNumber;
	
	public BillGenerater(Customer customer) {
		this.customer = customer;
		totalWaterNumber = customer.getCustomerWater().getNewNumber() - customer.getCustomerWater().getOrgNumber();
	}
	
	public Bill genBill() {
		calPostage4PriceByBottom(); //must cal this item first
		calPostage4PriceByNumber();
		calPostage4PriceByStep();
		calPostage4PriceByDedicated();
		Bill bill = new Bill();
		bill.setBeginWaterWord(customer.getCustomerWater().getOrgNumber());
		bill.setEndWaterWord(customer.getCustomerWater().getNewNumber());
		bill.setTotalPostage(totalPostage);
		bill.setDetailContent(detailedBill);
		bill.setName(customer.getName() + "账单");
		bill.setInputDate(new Date());
		bill.setInputerName(customer.getCustomerWater().getInputerName());
		bill.setCustomerCode(customer.getCustomerInfo().getCode());
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
	
	private void calPostage4PriceByStep() {
		CustomerType customerType = customer.getCustomerType();
		Float postage = new Float(0);
		for(Charge charge : customerType.getCharges()) {
			if(charge.getChargeType() != ChargeTypeEnum.CHARGE_STEP_BY_STEP.getId())
				continue;
			
			if(totalWaterNumber > charge.getChargeParameter().getStep3Number()) {
				postage += (totalWaterNumber -  charge.getChargeParameter().getStep3Number()) *  charge.getChargeParameter().getStep3Price();
				totalWaterNumber =  charge.getChargeParameter().getStep3Number();
			}
			if(totalWaterNumber > charge.getChargeParameter().getStep2Number()) {
				postage += (totalWaterNumber -  charge.getChargeParameter().getStep2Number()) *  charge.getChargeParameter().getStep2Price();
				totalWaterNumber =  charge.getChargeParameter().getStep2Number();
			}
			postage += totalWaterNumber *  charge.getChargeParameter().getStep1Price();
			
			totalPostage += postage;
			detailedBill += ";" + charge.getName() + ":" + postage;
		}
	}
}
