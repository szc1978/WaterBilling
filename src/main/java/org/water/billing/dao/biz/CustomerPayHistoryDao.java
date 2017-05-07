package org.water.billing.dao.biz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.biz.CustomerPayHistory;

public interface CustomerPayHistoryDao extends JpaRepository<CustomerPayHistory, Integer>{
	
	public Page<CustomerPayHistory> findAll(Pageable page);
	
	public CustomerPayHistory findById(int id);
}
