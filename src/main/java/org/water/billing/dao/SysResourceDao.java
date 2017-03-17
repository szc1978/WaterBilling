package org.water.billing.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.water.billing.entity.admin.SysResource;
import org.water.billing.entity.admin.SysUser;

public interface SysResourceDao extends JpaRepository<SysResource, Integer> {

	public SysUser findByName(String name);
	
	//@Query(value="select * from s_resource s1 where s1.resource_id=(select s2.resource_id from s_resource_role s2 where s2.role_id = (select s3.id from s_role s3 where s3.name=?1))",nativeQuery = true)
	@Query("select s1 from SysResource s1 where s1.resourceId=(select s2.resourceId from SysResourceRole s2 where s2.roleCode=(select s3.id from SysUserRole s3 where s3.roleCode=?1))")
	public List<SysResource> findByRoleName(String roleName);
}
