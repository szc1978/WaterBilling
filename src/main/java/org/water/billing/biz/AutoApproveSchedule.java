package org.water.billing.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.water.billing.GlobalConfiguration;
import org.water.billing.consts.Consts;
import org.water.billing.entity.biz.Bill;
import org.water.billing.entity.biz.Customer;
import org.water.billing.entity.biz.CustomerWater;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.CustomerService;
import org.water.billing.service.biz.CustomerWaterService;

@Component
public class AutoApproveSchedule {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerWaterService customerWaterService;
	
	@Autowired
	BillService billService;

	@Scheduled(fixedDelay = 10 * 1000)
	public void autoApprove() {
		
		
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
		List<Customer> allCustomersWhichHaveNewWaterNumber = customerService.findAllCustomersWhichHaveNewBill();
		for(Customer customer : allCustomersWhichHaveNewWaterNumber) {
			BillGenerater billGenerater = new BillGenerater(customer);
			Bill bill = billGenerater.genBill();
			billService.save(bill);
			
			CustomerWater customerWater = customer.getCustomerWater();
			
			customerWater.setOrgNumber(customerWater.getNewNumber());
			Float yearCount = customerWater.getYearCount() + customerWater.getNewNumber();
			customerWater.setYearCount(yearCount);
			customerWater.setNewNumber(new Float(0));
			
			customerWaterService.save(customerWater);
		}
	}
}
