package org.water.billing.dao.biz;

import java.util.List;

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
	
	public List<Customer> findByStatusGreaterThan(int status);
	
	public int countByStatusGreaterThan(int status);

	@Query(value="select customer from Customer customer where status = :status and customerInfo.code = :code")
	public Customer findByStatusAndCode(@Param("status") int status,@Param("code") String code);
	
	@Query(value="select customer from Customer customer where status = :status and customerInfo.address like %:address%")
	public List<Customer> findByStatusAndAddress(@Param("status") int status,@Param("address") String address);
	
	@Query(value="select customer from Customer customer where status = :status and customerInfo.address like %:address% and waterProvider.id = :waterProviderId")
	public List<Customer> findByStatusAndAddressAndWaterProvider(@Param("status") int status,@Param("address") String address,@Param("waterProviderId") int waterProviderId);
	
		
	@Query(value="select customer from Customer customer where status = :status and customerInfo.code = :code and waterProvider.id = :waterProviderId")
	public Customer findByCodeAndWaterProvider(@Param("status") int status,@Param("code") String code,@Param("waterProviderId") int waterProviderId);
}
