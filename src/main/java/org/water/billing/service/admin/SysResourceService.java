package org.water.billing.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.admin.SysResourceDao;
import org.water.billing.entity.admin.SysResource;

@Service("sysResourceService")
public class SysResourceService {
	
	@Autowired
	SysResourceDao resourceDao;
	
	public SysResource findActiveUserByName(String name) {
		return resourceDao.findByName(name);
	}
	
	public SysResource findByName(String name) {
		return resourceDao.findByName(name);
	}
	
	public SysResource findById(int id) {
		return resourceDao.findById(id);
	}
	
	public List<SysResource> findAll() {
		return resourceDao.findAll();
	}
	
	public SysResource save(SysResource sysResource) {
		return resourceDao.save(sysResource);
	}

}
