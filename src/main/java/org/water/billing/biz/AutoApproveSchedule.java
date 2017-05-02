package org.water.billing.biz;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.water.billing.GlobalConfiguration;
import org.water.billing.consts.Consts;
import org.water.billing.entity.biz.Bill;
import org.water.billing.entity.biz.Customer;
import org.water.billing.entity.biz.CustomerWaterMeter;
import org.water.billing.entity.biz.WaterMeterData;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.CustomerService;
import org.water.billing.service.biz.CustomerWaterMeterService;
import org.water.billing.service.biz.WaterMeterDataService;

@Component
public class AutoApproveSchedule {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerWaterMeterService customerWaterMeterService;
	
	@Autowired
	WaterMeterDataService waterMeterDataService;
	
	@Autowired
	BillService billService;

	@Scheduled(fixedDelay = 300 * 1000)
	public void autoApprove() {
		autoApproveCustomer();
		autoApproveCustomerWater();
		autoApproveCustomerBill();
	}
	
	private void autoApproveCustomer() {
		String disableFlag = GlobalConfiguration.getInstance().getConfigValueByItemName(Consts.GCK_DISABLE_APPROVE_CUSTOMER);
		if(disableFlag.equals("0"))
			return;
		List<Customer> allPendingCustomers = customerService.findAllPendingCustomer();
		for(Customer customer : allPendingCustomers) {
			int newStatus = customer.getStatus() & 1;
			customer.setStatus(newStatus);
			customerService.save(customer);
		}
	}
	
	private void autoApproveCustomerWater() {
		String disableFlag = GlobalConfiguration.getInstance().getConfigValueByItemName(Consts.GCK_DISABLE_APPROVE_CUSTOMER_WATER);
		if(disableFlag.equals("0"))
			return;
		List<CustomerWaterMeter> allCustomersWhichHaveNewWaterNumber = customerWaterMeterService.findAllCustomersWhichHaveNewBill();
		for(CustomerWaterMeter customerWaterMeter : allCustomersWhichHaveNewWaterNumber) {
			BillGenerater billGenerater = new BillGenerater(customerWaterMeter,Consts.BILL_TYPE_WATER);
			Bill bill = billGenerater.genBill();
			billService.save(bill);
			
			WaterMeterData waterMeterData = customerWaterMeter.getWaterMeterData();
			
			waterMeterData.setOrgNumber(waterMeterData.getNewNumber());
			Float yearCount = waterMeterData.getYearCount() + waterMeterData.getNewNumber();
			waterMeterData.setYearCount(yearCount);
			waterMeterData.setNewNumber(new Float(0));
			
			waterMeterDataService.save(waterMeterData);
		}
	}
	
	private void autoApproveCustomerBill() {
		String disableFlag = GlobalConfiguration.getInstance().getConfigValueByItemName(Consts.GCK_DISABLE_APPROVE_CUSTOMER_BILL);
		if(disableFlag.equals("0"))
			return;
		List<Bill> allPendingBill = billService.findAllPendingBill();
		for(Bill bill : allPendingBill){
			String customerCode = bill.getCustomerCode();
			Customer customer = customerService.findByCode(customerCode);
			if(customer.getBalance() > bill.getTotalPostage()) {
				customer.setBalance(customer.getBalance() - bill.getTotalPostage());
				bill.setIsCharged(1);
				bill.setChargeDate(new Date());
				billService.save(bill);
			}
			bill.setAutoChargeFlag(Consts.NON_BILL_AUTO_CHARGE_FLAG);
			billService.save(bill);
		}
	}
}
