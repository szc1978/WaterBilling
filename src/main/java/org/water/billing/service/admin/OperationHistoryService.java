package org.water.billing.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.water.billing.dao.admin.OperationHistoryDao;
import org.water.billing.entity.admin.OperationHistory;

@Service("operationHistoryService")
public class OperationHistoryService {
	
	@Autowired
	OperationHistoryDao opHistoryDao;
	
	public Page<OperationHistory> findAll(int pageIndex,int number) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		Pageable page = new PageRequest(pageIndex,number,sort);
		Page<OperationHistory> history = opHistoryDao.findAll(page);
		return history;
	}
	
	public OperationHistory save(OperationHistory op) {
		return opHistoryDao.save(op);
	}

	public List<OperationHistory> findLatest10ByUserName(String userName) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		Pageable page = new PageRequest(0,10,sort);
		Page<OperationHistory> history = opHistoryDao.findAll(page);
		return history.getContent();
	}
}
