package org.water.billing.dao.biz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.water.billing.entity.biz.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer>{
	public Page<Customer> findAll(Pageable page);
	
	public Customer findById(int id);
	
	public Customer findByCustomerInfo(int id);

	@Query(value="select customer from Customer customer where customerInfo.code = :code")
	public Customer findByCode(@Param("code") String code);
}
