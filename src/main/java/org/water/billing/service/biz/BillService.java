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
	
	public List<Bill> findCustomerChargedBill(String customerCode,Date fromDate,Date toDate) {
		return billDao.findCustomerChargedBill(customerCode, fromDate, toDate);
	}
	
	public List<Bill> findUnchargedBill(String customerCode) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		return billDao.findByCustomerCodeAndIsCharged(customerCode, 0,sort);
	}
	
	public List<Bill> findAllUnchargedBill() {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		return billDao.findByIsCharged( 0,sort);
	}
	
	public Bill payBill(Bill bill,Float paiedValue,Float customerBalance,String reduceContent) {
		bill.setAutoChargeFlag(Consts.NON_BILL_AUTO_CHARGE_FLAG);
		bill.setIsCharged(1);
		bill.setChargeDate(new Date());
		bill.setPaied(paiedValue);
		bill.setReduceContent(reduceContent);
		bill.setCustomerBalanceAfterPay(customerBalance);
		bill = billDao.save(bill);
		return bill;
	}
	
	public Bill rollbackPay(Bill bill,Float customerBalance) {
		bill.setIsCharged(0);
		bill.setPaied(new Float(0));
		
		bill.setReduceContent("");
		bill.setChargeDate(null);
		bill.setCustomerBalanceAfterPay(customerBalance + bill.getPaied());
		return billDao.save(bill);
	}
	
	public Bill save(Bill bill) {
		return billDao.save(bill);
	}
	
	public void delete(Bill bill) {
		billDao.delete(bill);
	}
	
	public List<Bill> findAllPendingBill() {
		return billDao.findByAutoChargeFlag(Consts.BILL_AUTO_CHARGE_FLAG);
	}
	
	public int countPendingBill() {
		return billDao.countByAutoChargeFlag(Consts.BILL_AUTO_CHARGE_FLAG);
	}
	
	public Bill findById(int id) {
		return billDao.findById(id);
	}

	public List<Bill> findNewBills4Statistic(Date fromDate,Date toDate) {
		return billDao.findByInputDateBetween(fromDate, toDate);
	}
	
	public List<Bill> findChargedBills4Statistic(Date fromDate,Date toDate) {
		return billDao.findByChargeDateBetween(fromDate, toDate);
	}
}
