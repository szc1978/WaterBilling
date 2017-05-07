package org.water.billing.dao.biz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.water.billing.entity.biz.CustomerInfo;

public interface CustomerInfoDao extends JpaRepository<CustomerInfo, Integer>{
	
	public CustomerInfo findById(int id);
	
	public CustomerInfo findByCode(String code);

	@Query(value="select count(c) > 0 from CustomerInfo c where c.code = :code")
	public Boolean existsByCode(@Param("code") String code);
}
