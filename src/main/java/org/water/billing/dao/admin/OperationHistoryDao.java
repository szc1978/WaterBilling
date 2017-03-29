package org.water.billing.dao.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.admin.OperationHistory;

public interface OperationHistoryDao extends JpaRepository<OperationHistory, Integer>{
	public Page<OperationHistory> findAll(Pageable page);
}
