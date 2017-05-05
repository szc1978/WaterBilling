package org.water.billing.dao.biz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.water.billing.entity.biz.WaterMeterMaintainHistory;

public interface WaterMeterMaintainHistoryDao extends JpaRepository<WaterMeterMaintainHistory, Integer>{
	
	public List<WaterMeterMaintainHistory> findAll();
	
	public WaterMeterMaintainHistory findById(int id);
	
	@Query(value = "select history from WaterMeterMaintainHistory history where waterMeter.customer.id = :customerId")
	public List<WaterMeterMaintainHistory> findHistory4Customer(@Param("customerId") int customerId);
}
