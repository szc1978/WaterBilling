package org.water.billing.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.water.billing.dao.admin.LoginHistoryDao;
import org.water.billing.entity.admin.LoginHistory;

@Service("loginHistoryService")
public class LoginHistoryService {
	
	@Autowired
	LoginHistoryDao loginLogDao;
	
	public Page<LoginHistory> findAll(int pageIndex,int number) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		Pageable page = new PageRequest(pageIndex,number,sort);
		Page<LoginHistory> history = loginLogDao.findAll(page);
		return history;
	}
	
	public LoginHistory findLatestByUserName(String userName) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		Pageable page = new PageRequest(1,1,sort);
		Page<LoginHistory> history = loginLogDao.findByUserName(userName,page);
		if(history.getNumberOfElements() == 1)
			return history.getContent().get(0);
		return null;
	}
	
	public LoginHistory findThisLoginByUserName(String userName) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		Pageable page = new PageRequest(0,1,sort);
		Page<LoginHistory> history = loginLogDao.findByUserName(userName,page);
		if(history.getNumberOfElements() == 1)
			return history.getContent().get(0);
		return null;
	}

}
