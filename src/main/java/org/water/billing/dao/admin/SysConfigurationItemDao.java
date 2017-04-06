package org.water.billing.dao.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.admin.SysConfigurationItem;

public interface SysConfigurationItemDao extends JpaRepository<SysConfigurationItem, Integer> {
	
	public List<SysConfigurationItem> findAll();

	public SysConfigurationItem findByItem(String item);

}
