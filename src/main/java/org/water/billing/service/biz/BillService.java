package org.water.billing.service.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.water.billing.dao.biz.BillDao;
import org.water.billing.entity.biz.Bill;

@Service("BillService")
public class BillService {
	
	@Autowired
	BillDao billDao;
	
	public List<Bill> findUnchargedBill(int customerId) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		return billDao.findByCustomerIdAndIsCharged(customerId, 0,sort);
	}
	
	public Bill save(Bill bill) {
		return billDao.save(bill);
	}
	

}
