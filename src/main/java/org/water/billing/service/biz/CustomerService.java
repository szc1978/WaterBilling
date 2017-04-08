package org.water.billing.service.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.water.billing.consts.Consts;
import org.water.billing.dao.biz.CustomerDao;
import org.water.billing.dao.biz.CustomerInfoDao;
import org.water.billing.entity.biz.Customer;

@Service("CustomerService")
public class CustomerService {
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	CustomerInfoDao customerInfoDao;
	
	public Page<Customer> findAll(int pageIndex,int number) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		Pageable page = new PageRequest(pageIndex,number,sort);
		Page<Customer> charges = customerDao.findAll(page);
		return charges;
	}

	public List<Customer> findAll() {
		return customerDao.findAll();
	}
	
	public Customer save(Customer customer) {
		return customerDao.save(customer);
	}
	
	public Customer findById(int id) {
		return customerDao.findById(id);
	}
	
	public Customer findByCode(String code) {
		return customerDao.findByCode(code);
	}
	
	public List<Customer> findAllPendingCustomer() {
		return customerDao.findByStatusGreaterThan(Consts.CUSTOMER_STATUS_ACTIVE_BIT);
	}
	
	public List<Customer> findAllCustomersWhichHaveNewBill() {
		return customerDao.findByPendingWaterNumberGreaterThan(new Float(0));
	}
}
