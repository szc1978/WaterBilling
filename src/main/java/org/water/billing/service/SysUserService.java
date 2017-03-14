package org.water.billing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.SysUserDao;
import org.water.billing.entity.SysUser;

@Service("sysUserService")
public class SysUserService {
	
	@Autowired
	SysUserDao userDao;
	
	public SysUser findByName(String name) {
		return userDao.findByName(name);
	}

}
