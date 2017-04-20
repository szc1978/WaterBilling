package org.water.billing.dao.biz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.biz.WaterMeterType;

public interface WaterMeterTypeDao extends JpaRepository<WaterMeterType, Integer>{
	
	public WaterMeterType findById(int id);
	
}
