package org.water.billing.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.water.billing.entity.admin.SysUser;

public interface SysUserDao extends JpaRepository<SysUser, Integer> {

	public SysUser findByName(String name);
	
	public SysUser findByNameAndActive(String name,int active);
	
	public SysUser findById(int id);
	
	public Page<SysUser> findAll(Pageable page);
	
	@Query(value="select user from SysUser user where user.name like %:key% or user.chineseName like %:key%")
	public Page<SysUser> findByNameOrChineseName(@Param("key") String key,Pageable page);

}
