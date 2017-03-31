package org.water.billing.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.water.billing.dao.admin.SysRoleDao;
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
	
	public Page<SysRole> findAll(int pageIndex,int number) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		Pageable page = new PageRequest(pageIndex,number,sort);
		Page<SysRole> roles = sysRoleDao.findAll(page);
		return roles;
	}
	
	public SysRole save(SysRole sysRole) {
		return sysRoleDao.save(sysRole);
	}
}
