package org.water.billing.dao.biz;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.biz.Bill;

public interface BillDao extends JpaRepository<Bill, Integer>{
	public Page<Bill> findAll(Pageable page);
	
	public Bill findById(int id);
	
	public List<Bill> findByIsCharged(int isCharged,Sort sort);
	
	public List<Bill> findByCustomerCodeAndIsCharged(String customerCode,int isCharged,Sort sort);

	public List<Bill> findByCustomerCodeAndIsChargedAndChargeDateBetween(String customerCode,int isCharged,Date fromDate,Date toDate,Sort sort);
	
	public int countByAutoChargeFlag(int flag);
	
	public List<Bill> findByAutoChargeFlag(int flag);
	
	public List<Bill> findByInputDateBetween(Date fromDate,Date toDate);
	
	public List<Bill> findByChargeDateBetween(Date fromDate,Date toDate);
}
