package org.water.billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.SysUserRoleDao;
import org.water.billing.entity.SysUserRole;

@Service("sysRoleService")
public class SysRoleService {
	
	@Autowired
	SysUserRoleDao sysRoleDao;
	
	public List<SysUserRole> findAll() {
		return sysRoleDao.findAll();
	}

}
