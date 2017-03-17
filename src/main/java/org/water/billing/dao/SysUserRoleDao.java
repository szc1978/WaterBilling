package org.water.billing.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.entity.admin.SysUserRole;

public interface SysUserRoleDao extends JpaRepository<SysUserRole, Integer> {

	public SysUser findByName(String name);
}
