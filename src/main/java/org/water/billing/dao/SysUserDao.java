package org.water.billing.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.admin.SysUser;

public interface SysUserDao extends JpaRepository<SysUser, Integer> {

	public SysUser findByName(String name);
	
	public SysUser findById(long id);
	
	public Page<SysUser> findAll(Pageable page);
}
