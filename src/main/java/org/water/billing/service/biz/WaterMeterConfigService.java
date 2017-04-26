package org.water.billing.service.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.biz.WaterMeterConfigDao;
import org.water.billing.entity.biz.WaterMeterConfig;

@Service("WaterMeterConfigService")
public class WaterMeterConfigService {
	
	@Autowired
	WaterMeterConfigDao waterMeterConfigDao;
	
	public WaterMeterConfig findById(int id) {
		return waterMeterConfigDao.findById(id);
	}
	
	public WaterMeterConfig save(WaterMeterConfig config) {
		return waterMeterConfigDao.save(config);
	}
	
	public WaterMeterConfig findByConfigItemIdAndValue(int id,String value) {
		return waterMeterConfigDao.findByConfigItemIdAndConfigItemValue(id, value);
	}
	
	public List<WaterMeterConfig> findByConfigItemId(int id) {
		return waterMeterConfigDao.findByConfigItemId(id);
	}
	
	public List<WaterMeterConfig> findAll() {
		return waterMeterConfigDao.findAll();
	}
}
