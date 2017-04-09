package org.water.billing.dao.biz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.biz.CustomerWater;

public interface CustomerWaterDao extends JpaRepository<CustomerWater, Integer>{
	
	public CustomerWater findById(int id);
	

}
