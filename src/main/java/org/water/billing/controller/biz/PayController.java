package org.water.billing.controller.biz;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.biz.Bill;
import org.water.billing.entity.biz.Customer;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.ChargeService;
import org.water.billing.service.biz.CustomerService;

@Controller
public class PayController {
	
	@Autowired
	ChargeService chargeService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	BillService billService;
	
	@RequestMapping(value = "/biz/pay",method=RequestMethod.GET)
	public String payForm(@RequestParam(required=false) String code,
						  ModelMap map) throws Exception {
		Customer customer = customerService.findByCode(code);
		if(customer == null && code != null)
			throw new Exception("用户不存在");
		if(customer == null && code == null)
			customer = new Customer();
		
		List<Bill> bills = billService.findUnchargedBill(customer.getId());
		if(bills.size() == 0 && code != null)
			throw new Exception("用户不欠费");
		
		Float unpaied = new Float(0);
		Float latePayment = new Float(0);
		Bill latestBill = null;
		
		if(code == null) {
			latestBill = new Bill();
		} else {
			latestBill = bills.get(0);
			Date now = new Date();
			for(int i=1;i<bills.size();i++) {
				unpaied += bills.get(i).getTotalPostage();
				Date inputDate = bills.get(i).getInputDate();
				Long days = (now.getTime() - inputDate.getTime())/(86400 * 1000);
				if(days > 30) {
					Float tmp =  (days - 30) * (bills.get(i).getTotalPostage() * new Float(0.005));
					latePayment += tmp;
				}
			}
		}
		map.addAttribute("customer_name", customer.getName());
		map.addAttribute("customer_code", customer.getCustomerInfo().getCode());
		map.addAttribute("customer_address", customer.getCustomerInfo().getAddress());
		map.addAttribute("customer_balance", customer.getBalance());
		map.addAttribute("bill",latestBill);
		map.addAttribute("un_paied",unpaied);
		map.addAttribute("late_payment",latePayment);
		return "/biz/pay";
	}
	
	@RequestMapping(value = "/biz/pay",method=RequestMethod.POST)
	public String pay(@RequestParam int id,
						ModelMap map) {
		Customer customer = customerService.findById(id);
		if(customer == null)
			customer = new Customer();
		return "redirect:/biz/pay?code=" + customer.getCustomerInfo().getCode();
	}
}
