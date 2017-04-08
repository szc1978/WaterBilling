package org.water.billing.controller.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.water.billing.entity.biz.Customer;
import org.water.billing.service.biz.CustomerService;

@Controller
public class ApproveController {

	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value = "/biz/approver",method=RequestMethod.GET)
	public String listPendingMsg(ModelMap model) {
		List<Customer> allPendingCustomers = customerService.findAllPendingCustomer();
		List<Customer> allCustomersWhichHaveNewWaterNumber = customerService.findAllCustomersWhichHaveNewBill();
		model.addAttribute("allPendingCustomers", allPendingCustomers);
		model.addAttribute("allCustomersWhichHaveNewWaterNumber", allCustomersWhichHaveNewWaterNumber);
		return "/biz/approver";
	}
}
