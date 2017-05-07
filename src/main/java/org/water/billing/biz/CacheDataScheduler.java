package org.water.billing.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.CustomerService;
import org.water.billing.service.biz.CustomerWaterMeterService;

@Component
public class CacheDataScheduler {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerWaterMeterService waterMeterService;
	
	@Autowired
	BillService billService;
	
	@Scheduled(fixedDelay = 60 * 1000)
	public void getCache() {
		int countPendingCustomer = customerService.countPendingCustomerMsg();
		int countPendingCustomerWater = waterMeterService.countPendingCustomerWaterNumberMsg();
		int countPendingBill = billService.countPendingBill();
		DataCache.getInstance().setPendingCountCustomerStatus(countPendingCustomer);
		DataCache.getInstance().setPendingCountCustomerWater(countPendingCustomerWater);
		DataCache.getInstance().setPendingCountCustomerBill(countPendingBill);
	}
}
