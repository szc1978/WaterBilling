package org.water.billing.dao.biz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.biz.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer>{
	public Page<Customer> findAll(Pageable page);
	public Customer findById(int id);
}
