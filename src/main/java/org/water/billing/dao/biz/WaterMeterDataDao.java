package org.water.billing.dao.biz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.biz.WaterMeterData;

public interface WaterMeterDataDao extends JpaRepository<WaterMeterData, Integer>{
	
	public WaterMeterData findById(int id);
	

}
