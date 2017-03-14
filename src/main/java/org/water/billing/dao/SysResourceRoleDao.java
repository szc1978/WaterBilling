package org.water.billing.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.SysResourceRole;
import org.water.billing.entity.SysUser;

public interface SysResourceRoleDao extends JpaRepository<SysResourceRole, Integer> {

	public SysUser findByName(String name);
}
