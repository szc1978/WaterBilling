package org.water.billing.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.SysUser;

public interface SysUserDao extends JpaRepository<SysUser, Integer> {

	public SysUser findByName(String name);
}
