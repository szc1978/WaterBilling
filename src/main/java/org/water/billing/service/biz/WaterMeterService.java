package org.water.billing.service.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.biz.WaterMeterDao;
import org.water.billing.entity.biz.WaterMeter;

@Service("WaterMeterService")
public class WaterMeterService {
	
	@Autowired
	WaterMeterDao waterMeterDao;
	
	public WaterMeter findById(int id) {
		return waterMeterDao.findById(id);
	}
	
	public WaterMeter save(WaterMeter meter) {
		return waterMeterDao.save(meter);
	}
}
