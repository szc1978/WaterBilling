package org.water.billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.water.billing.dao.SysUserDao;
import org.water.billing.entity.admin.SysUser;

@Service("sysUserService")
public class SysUserService {
	
	@Autowired
	SysUserDao userDao;
	
	public SysUser findActiveUserByName(String name) {
		return userDao.findByNameAndActive(name,1);
	}
	
	public SysUser findByName(String name) {
		return userDao.findByName(name);
	}
	
	public SysUser findById(int id) {
		return userDao.findById(id);
	}
	
	public Page<SysUser> findAll(int pageIndex,int number) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		Pageable page = new PageRequest(pageIndex,number,sort);
		Page<SysUser> users = userDao.findAll(page);
		return users;
	}
	
	public Page<SysUser> fuzzeFind(String keyword,int pageIndex,int number) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		Pageable page = new PageRequest(pageIndex,number,sort);
		Page<SysUser> users = userDao.findByNameOrChineseName(keyword,page);
		return users;
	}
	
	public List<SysUser> findAll() {
		return userDao.findAll();
	}
	
	public SysUser save(SysUser sysUser) {
		return userDao.save(sysUser);
	}
}
