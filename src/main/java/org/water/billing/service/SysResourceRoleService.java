package org.water.billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.SysResourceRoleDao;
import org.water.billing.entity.admin.SysResourceRole;

@Service("sysResourceRoleService")
public class SysResourceRoleService {
	@Autowired
	SysResourceRoleDao sysResourceRoleDao;
	
	public List<SysResourceRole> findAll() {
		return sysResourceRoleDao.findAll();
	}
}
