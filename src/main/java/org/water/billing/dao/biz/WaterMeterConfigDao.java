package org.water.billing.dao.biz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.biz.WaterMeterConfig;

public interface WaterMeterConfigDao extends JpaRepository<WaterMeterConfig, Integer>{
	public WaterMeterConfig findById(int id);
	
	public WaterMeterConfig findByConfigItemIdAndConfigItemValue(int id,String value);
	
	public List<WaterMeterConfig> findByConfigItemId(int id);
}
