package org.water.billing.service.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.water.billing.dao.biz.CustomerTypeDao;
import org.water.billing.entity.biz.CustomerType;

@Service("CustomerTypeService")
public class CustomerTypeService {
	
	@Autowired
	CustomerTypeDao customerTypeDao;
	
	public Page<CustomerType> findAll(int pageIndex,int number) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		Pageable page = new PageRequest(pageIndex,number,sort);
		Page<CustomerType> charges = customerTypeDao.findAll(page);
		return charges;
	}

	public List<CustomerType> findAll() {
		return customerTypeDao.findAll();
	}
	
	public CustomerType save(CustomerType customer) {
		return customerTypeDao.save(customer);
	}
	
	public CustomerType findById(int id) {
		return customerTypeDao.findById(id);
	}
}
