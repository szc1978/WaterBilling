package org.water.billing.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.SysUserRole;
import org.water.billing.entity.SysUser;

public interface SysUserRoleDao extends JpaRepository<SysUserRole, Integer> {

	public SysUser findByName(String name);
}
