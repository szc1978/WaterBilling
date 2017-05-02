package org.water.billing.dao.biz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.water.billing.entity.biz.CustomerWaterMeter;

public interface CustomerWaterMeterDao extends JpaRepository<CustomerWaterMeter, Integer>{
	
	public CustomerWaterMeter findById(int id);
	
	public CustomerWaterMeter findByBodyNumber(String bodyNumber);
	
	@Query(value = "select count(*) from CustomerWaterMeter customerWaterMeter where waterMeterData.newNumber > 0")
	public int countPendingWaterNumber();

	@Query(value="select customerWaterMeter from CustomerWaterMeter customerWaterMeter where waterMeterData.newNumber > 0")
	public List<CustomerWaterMeter> queryHasPendingWaterNumberMeter();

	@Query(value="select customerWaterMeter from CustomerWaterMeter customerWaterMeter where customer.id=:id")
	public List<CustomerWaterMeter> findCustomerWaterMeters(@Param("id") int id);
}
