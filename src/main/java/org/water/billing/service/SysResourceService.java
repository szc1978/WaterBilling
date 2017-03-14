package org.water.billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.SysResourceDao;
import org.water.billing.dao.SysResourceRoleDao;
import org.water.billing.dao.SysUserRoleDao;
import org.water.billing.entity.SysResource;

@Service
public class SysResourceService {
	
	@Autowired
	SysResourceDao sysResourceDao;
	
	@Autowired
	SysUserRoleDao sysRoleDao;
	
	@Autowired
	SysResourceRoleDao sysResourceRoleDao;
	
	public List<SysResource> findByRoleName(String roleName) {
		return sysResourceDao.findByRoleName(roleName);
	}
	
}
