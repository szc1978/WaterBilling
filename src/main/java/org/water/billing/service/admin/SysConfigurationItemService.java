package org.water.billing.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.admin.SysConfigurationItemDao;
import org.water.billing.entity.admin.SysConfigurationItem;

@Service("sysConfigurationItem")
public class SysConfigurationItemService {
	
	@Autowired
	SysConfigurationItemDao configDao;
	
	public List<SysConfigurationItem> findAll() {
		return configDao.findAll();
	}
	
	public SysConfigurationItem findItem(String item) {
		return configDao.findByItem(item);
	}
	
	public SysConfigurationItem save(SysConfigurationItem item) {
		return configDao.save(item);
	}

}
