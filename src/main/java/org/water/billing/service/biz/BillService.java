package org.water.billing.service.biz;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.water.billing.consts.Consts;
import org.water.billing.dao.biz.BillDao;
import org.water.billing.entity.biz.Bill;

@Service("BillService")
public class BillService {
	
	@Autowired
	BillDao billDao;
	
	public List<Bill> findCustomerBill(String customerCode,Date fromDate,Date toDate) {
		return billDao.findByCustomerCodeAndChargeDateBetween(customerCode, fromDate, toDate);
	}
	
	public List<Bill> findUnchargedBill(String customerCode) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		return billDao.findByCustomerCodeAndIsCharged(customerCode, 0,sort);
	}
	
	public Bill save(Bill bill) {
		return billDao.save(bill);
	}
	
	public List<Bill> findAllPendingBill() {
		return billDao.findByAutoChargeFlag(Consts.BILL_AUTO_CHARGE_FLAG);
	}
	
	public int countPendingBill() {
		return billDao.countByAutoChargeFlag(Consts.BILL_AUTO_CHARGE_FLAG);
	}

}
