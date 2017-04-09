package org.water.billing.service.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.biz.CustomerWaterDao;
import org.water.billing.entity.biz.CustomerWater;

@Service("CustomerWaterService")
public class CustomerWaterService {
	
	@Autowired
	CustomerWaterDao customerWaterDao;
	
	public CustomerWater findById(int id) {
		return customerWaterDao.findById(id);
	}
	
	public CustomerWater save(CustomerWater customerWater) {
		return customerWaterDao.save(customerWater);
	}
}
