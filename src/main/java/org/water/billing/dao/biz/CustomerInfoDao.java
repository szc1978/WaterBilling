package org.water.billing.dao.biz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.biz.CustomerInfo;

public interface CustomerInfoDao extends JpaRepository<CustomerInfo, Integer>{
	
	public CustomerInfo findById(int id);
	
	public CustomerInfo findByCode(String code);

}
