package org.water.billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.SysRoleDao;
import org.water.billing.entity.admin.SysRole;

@Service("sysRoleService")
public class SysRoleService {

	@Autowired
	SysRoleDao sysRoleDao;
	
	public SysRole findById(int id) {
		return sysRoleDao.findById(id);
	}
	
	public List<SysRole> findAll() {
		return sysRoleDao.findAll();
	}
	
	public SysRole save(SysRole sysRole) {
		return sysRoleDao.save(sysRole);
	}
}
