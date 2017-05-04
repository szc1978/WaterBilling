package org.water.billing.service.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.consts.Consts;
import org.water.billing.dao.biz.CustomerWaterMeterDao;
import org.water.billing.entity.biz.CustomerWaterMeter;

@Service("WaterMeterService")
public class CustomerWaterMeterService {
	
	@Autowired
	CustomerWaterMeterDao waterMeterDao;
	
	public CustomerWaterMeter findById(int id) {
		return waterMeterDao.findById(id);
	}
	
	public CustomerWaterMeter save(CustomerWaterMeter meter) {
		return waterMeterDao.save(meter);
	}
	
	public List<CustomerWaterMeter> findAllCustomersWhichHaveNewBill() {
		return waterMeterDao.queryHasPendingWaterNumberMeter();
	}
	
	public int countPendingCustomerWaterNumberMsg() {
		return waterMeterDao.countPendingWaterNumber();
	}
	
	public CustomerWaterMeter finByMeterBodyNumber(String bodyNumber) {
		return waterMeterDao.findByBodyNumber(bodyNumber);
	}
	
	public List<CustomerWaterMeter> findCustomerMeters(int customerId) {
		return waterMeterDao.findCustomerWaterMeters(customerId);
	}
	
	public List<CustomerWaterMeter> findCustomerMetersByCodeAndWaterProvider(String code,int waterProviderId) {
		return waterMeterDao.findByCodeAndWaterProvider(Consts.CUSTOMER_STATUS_ACTIVE_BIT,code,waterProviderId);
	}
	
	public List<CustomerWaterMeter> findCustomerMetersByAddressAndWaterProvider(String address,int waterProviderId) {
		return waterMeterDao.findByAddressAndWaterProvider(Consts.CUSTOMER_STATUS_ACTIVE_BIT,address,waterProviderId);
	}
	
	public List<CustomerWaterMeter> diySearch(String customerCode,
			 									String customerName,
			 									String customerAddress,
			 									int waterProviderId,
			 									int customerStatus,
			 									int meterStatus) {
		return waterMeterDao.findByCondition(customerCode,customerName,customerAddress,waterProviderId,customerStatus,meterStatus);
	}
}
