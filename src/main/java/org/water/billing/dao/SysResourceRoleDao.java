package org.water.billing.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.admin.SysResourceRole;
import org.water.billing.entity.admin.SysUser;

public interface SysResourceRoleDao extends JpaRepository<SysResourceRole, Integer> {

	public SysUser findByName(String name);
	
	public List<SysResourceRole> findAll();
	
	//@Query("select s1 from SysResource s1 where s1.resourceId=(select s2.resourceId from SysResourceRole s2 where s2.roleCode=(select s3.id from SysUserRole s3 where s3.roleCode=?1))")
	//public List<SysResource> findByRoleName(String roleName);
}
