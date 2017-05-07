package org.water.billing.service.biz;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.water.billing.dao.biz.CustomerPayHistoryDao;
import org.water.billing.entity.biz.Customer;
import org.water.billing.entity.biz.CustomerPayHistory;

@Service("customerPayHistoryService")
public class CustomerPayHistoryService {
	
	@Autowired
	CustomerPayHistoryDao historyDao;
	
	public List<CustomerPayHistory> findAll() {
		return historyDao.findAll();
	}
	
	public List<CustomerPayHistory> findHistory4Statistic(Date from,Date to) {
		return historyDao.findByCreateTimeBetween(from, to);
	}
	
	public CustomerPayHistory newPay(Customer customer,Float thisPay) {
		if(thisPay == 0)
			return null;
		CustomerPayHistory payHistory = new CustomerPayHistory();
		payHistory.setCustomer(customer);payHistory.setPayNumber(thisPay);payHistory.setCustomerBalance(customer.getBalance() + thisPay);
		return historyDao.save(payHistory);
	}
}
