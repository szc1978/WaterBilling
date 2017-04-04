package org.water.billing.service.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.biz.WaterProviderDao;
import org.water.billing.entity.biz.WaterProvider;

@Service("WaterProviderService")
public class WaterProviderService {
	
	@Autowired
	WaterProviderDao waterProviderDao;

	public List<WaterProvider> findAll() {
		return waterProviderDao.findAll();
	}
	
	public WaterProvider save(WaterProvider provider) {
		return waterProviderDao.save(provider);
	}
	
	public WaterProvider findById(int id) {
		return waterProviderDao.findById(id);
	}
}
