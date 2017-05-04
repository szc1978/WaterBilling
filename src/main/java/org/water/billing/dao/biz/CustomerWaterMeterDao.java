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
	
	@Query(value="select customerWaterMeter from CustomerWaterMeter customerWaterMeter where customerWaterMeter.customer.status = :status and customerWaterMeter.customer.customerInfo.code = :code and customerWaterMeter.customer.waterProvider.id = :waterProviderId")
	public List<CustomerWaterMeter> findByCodeAndWaterProvider(@Param("status") int status,@Param("code") String code,@Param("waterProviderId") int waterProviderId);

	@Query(value="select customerWaterMeter from CustomerWaterMeter customerWaterMeter where customerWaterMeter.customer.status = :status and customerWaterMeter.customer.customerInfo.address like %:address% and customerWaterMeter.customer.waterProvider.id = :waterProviderId")
	public List<CustomerWaterMeter> findByAddressAndWaterProvider(@Param("status") int status,@Param("address") String address,@Param("waterProviderId") int waterProviderId);

	@Query(value="select customerWaterMeter from CustomerWaterMeter customerWaterMeter where "
			+ "customerWaterMeter.customer.customerInfo.code like %:customerCode% "
			+ "and customerWaterMeter.customer.name like %:customerName% "
			+ "and customerWaterMeter.customer.customerInfo.address like %:customerAddress% "
			+ "and customerWaterMeter.customer.waterProvider.id = :waterProviderId "
			+ "and customerWaterMeter.customer.status = :customerStatus "
			+ "and customerWaterMeter.status = :meterStatus")
	public List<CustomerWaterMeter> findByCondition(@Param("customerCode") String customerCode,
													@Param("customerName") String customerName,
													@Param("customerAddress") String customerAddress,
													@Param("waterProviderId") int waterProviderId,
													@Param("customerStatus") int customerStatus,
													@Param("meterStatus") int meterStatus);
}
