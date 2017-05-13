package org.water.billing.controller.rest;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.water.billing.biz.BillHelper;
import org.water.billing.entity.biz.Bill;
import org.water.billing.entity.biz.Customer;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.CustomerService;

@RestController
@RequestMapping(value="/rest")
public class RestApi {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	BillService billService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/customer",method=RequestMethod.GET)
	public JSONObject getCustomerInformation(@RequestParam String code) {
		Customer customer = customerService.findByCode(code);
		Float arrears = new Float(0);
		if(customer == null) {
			customer = new Customer();
		} else {
			List<Bill> bills = billService.findUnchargedBill(code);
			Map<String,Float> billsInfo = BillHelper.getPayInformation(bills);
			if(billsInfo.containsKey("unpaied"))
				arrears += billsInfo.get("unpaied");
			if(billsInfo.containsKey("latePayment"))
				arrears += billsInfo.get("latePayment");
		}
		JSONObject json = customer.toJson();
		json.put("欠费", arrears);
		return json;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/writeoffbill",method=RequestMethod.POST)
	public JSONObject writeOffBill(@RequestParam String code,@RequestParam String m) {
		JSONObject resp = new JSONObject();
		resp.put("result", "OKOKOK");
		Customer customer = customerService.findByCode(code);
		if(customer == null) {
			resp.put("result", "NONONO");
			resp.put("msg", "客户不存在");
			return resp;
		} 
		Float arrears = new Float(0);
		List<Bill> bills = billService.findUnchargedBill(code);
		Map<String,Float> billsInfo = BillHelper.getPayInformation(bills);
		if(billsInfo.containsKey("unpaied"))
			arrears += billsInfo.get("unpaied");
		if(billsInfo.containsKey("latePayment"))
			arrears += billsInfo.get("latePayment");
		Float money = Float.valueOf(m);
		if(money < arrears) {
			resp.put("result", "NONONO");
			resp.put("msg", "费用不足，欠费:" + arrears);
			return resp;
		}
		//先充值
		billService.customerPayOnlyBill(customer, money);
		customerService.pay(customer, money * -1);
		
		for(Bill bill : bills) {
			Float thisLatePayment = BillHelper.getLatePayment4Bill(bill);
			Float needPay = bill.getTotalPostage() + thisLatePayment;
			doPay4OneBill(bill,needPay,null);
		}
		resp.put("msg", "销账成功");
		return resp;
	}
	
	//Code is same as package org.water.billing.controller.biz;
	private Bill doPay4OneBill(Bill bill,Float needPay,String reduceContent)  {
		String customerCode = bill.getCustomerCode();
		Customer customer = customerService.findByCode(customerCode);

		customer = customerService.pay(customer, needPay);
		bill = billService.payBill(bill, needPay, customer.getBalance(), reduceContent);

		return bill;
	}

}
