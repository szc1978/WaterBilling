package org.water.billing.dao.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.admin.SysResource;

public interface SysResourceDao extends JpaRepository<SysResource, Integer> {

	public SysResource findByName(String name);
	
	public SysResource findById(int id);
	
	public Page<SysResource> findAll(Pageable page);

}
