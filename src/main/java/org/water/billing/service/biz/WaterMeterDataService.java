package org.water.billing.service.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.biz.WaterMeterDataDao;
import org.water.billing.entity.biz.WaterMeterData;

@Service("CustomerWaterService")
public class WaterMeterDataService {
	
	@Autowired
	WaterMeterDataDao customerWaterDao;
	
	public WaterMeterData findById(int id) {
		return customerWaterDao.findById(id);
	}
	
	public WaterMeterData save(WaterMeterData customerWater) {
		return customerWaterDao.save(customerWater);
	}
}
