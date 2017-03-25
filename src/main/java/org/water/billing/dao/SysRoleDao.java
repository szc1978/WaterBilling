package org.water.billing.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.admin.SysRole;

public interface SysRoleDao extends JpaRepository<SysRole, Integer> {
	public SysRole findByName(String name);
	public SysRole findById(int id);
}
