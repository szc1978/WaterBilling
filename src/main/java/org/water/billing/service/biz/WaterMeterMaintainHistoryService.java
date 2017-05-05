package org.water.billing.service.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.biz.WaterMeterMaintainHistoryDao;
import org.water.billing.entity.biz.WaterMeterMaintainHistory;

@Service("WaterMeterMaintainHistoryService")
public class WaterMeterMaintainHistoryService {
	
	@Autowired
	WaterMeterMaintainHistoryDao dao;

	public List<WaterMeterMaintainHistory> findAll() {
		return dao.findAll();
	}
	
	public WaterMeterMaintainHistory save(WaterMeterMaintainHistory history) {
		return dao.save(history);
	}
	
	public WaterMeterMaintainHistory findById(int id) {
		return dao.findById(id);
	}
	
	public List<WaterMeterMaintainHistory> findHistory4Customer(int customerId) {
		return dao.findHistory4Customer(customerId);
	}
}
