package org.water.billing.controller.biz;

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
						  ModelMap map) {
		Customer customer = customerService.findByCode(code);
		if(customer == null)
			customer = new Customer();
		List<Bill> bills = billService.findUnchargedBill(customer.getId());
		Bill latestBill = null;
		if(bills.size() > 0) {
			latestBill = bills.get(0);
		}
		if(bills.size() > 1) {
			for(int i=1;i<bills.size();i++) {
				
			}
		}
		map.addAttribute("customer",customer);
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
