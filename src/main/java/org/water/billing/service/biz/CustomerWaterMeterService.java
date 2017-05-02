package org.water.billing.service.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
