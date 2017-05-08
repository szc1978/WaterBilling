package org.water.billing.service.biz;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.water.billing.consts.Consts;
import org.water.billing.dao.biz.BillDao;
import org.water.billing.entity.biz.Bill;
import org.water.billing.entity.biz.Customer;

@Service("BillService")
public class BillService {
	
	@Autowired
	BillDao billDao;
	
	public List<Bill> findCustomerChargedBill(String customerCode,Date fromDate,Date toDate) {
		Order[] orders = {new Sort.Order(Sort.Direction.ASC,"chargeDate"),new Sort.Order(Sort.Direction.DESC,"id")};
		Sort sort = new Sort(orders);
		return billDao.findByCustomerCodeAndIsChargedAndChargeDateBetween(customerCode, 1,fromDate, toDate,sort);
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
		bill.setCustomerBalanceAfterPay(customerBalance);
		if(reduceContent == null || "".equals(reduceContent)) {
			reduceContent = "";
			String content = bill.getDetailContent();
			for(String s : content.split(";")) {
				if(s == null || s.trim().length() == 0)
					continue;
				reduceContent += s + ",0;";
			}
		}
		bill.setReduceContent(reduceContent);
		bill = billDao.save(bill);
		return bill;
	}
	
	public Bill customerPayOnlyBill(Customer customer,Float money) {
		if(money == 0)
			return null;
		Bill bill = new Bill();
		bill.setBillType(Consts.BILL_TYPE_PAY);
		bill.setName("客户充值");
		bill.setCustomerCode(customer.getCustomerInfo().getCode());
		bill.setDetailContent("");
		bill.setPaied(money);
		bill.setCustomerBalanceAfterPay(customer.getBalance() + money);
		bill.setIsCharged(1);
		bill.setChargeDate(new Date());
		bill = billDao.save(bill);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		return bill;
	}
	
	public Bill rollbackPay(Bill bill,Float newBalance) {
		bill.setIsCharged(0);
		bill.setPaied(new Float(0));
		
		bill.setReduceContent("");
		bill.setChargeDate(null);
		bill.setCustomerBalanceAfterPay(newBalance);
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
