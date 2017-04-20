package org.water.billing.service.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.biz.WaterMeterTypeDao;
import org.water.billing.entity.biz.WaterMeterType;

@Service("WaterMeterTypeService")
public class WaterMeterTypeService {
	
	@Autowired
	WaterMeterTypeDao waterMeterTypeDao;
	
	public WaterMeterType findById(int id) {
		return waterMeterTypeDao.findById(id);
	}
	
	public List<WaterMeterType> findAll() {
		return waterMeterTypeDao.findAll();
	}
	
	public WaterMeterType save(WaterMeterType meterType) {
		return waterMeterTypeDao.save(meterType);
	}
}
