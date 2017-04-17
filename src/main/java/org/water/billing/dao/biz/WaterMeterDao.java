package org.water.billing.dao.biz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.biz.WaterMeter;

public interface WaterMeterDao extends JpaRepository<WaterMeter, Integer>{
	public WaterMeter findById(int id);
	
}
