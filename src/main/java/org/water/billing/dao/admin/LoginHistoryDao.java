package org.water.billing.dao.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.admin.LoginHistory;

public interface LoginHistoryDao extends JpaRepository<LoginHistory, Integer>{
	public Page<LoginHistory> findAll(Pageable page);
	
	public Page<LoginHistory> findByUserName(String userName,Pageable page);
}
