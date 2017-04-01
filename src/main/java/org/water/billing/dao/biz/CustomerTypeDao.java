package org.water.billing.dao.biz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.biz.CustomerType;

public interface CustomerTypeDao extends JpaRepository<CustomerType, Integer>{
	public Page<CustomerType> findAll(Pageable page);
	public CustomerType findById(int id);
}
