package org.water.billing.controller.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.biz.Customer;
import org.water.billing.service.biz.CustomerService;
import org.water.billing.utils.Utils;

@Controller
public class CustomerController {
	
	@Autowired
	CustomerService customerService;

	@RequestMapping(value="/biz/customer",method=RequestMethod.GET)
	public String customer(@RequestParam(defaultValue="1") int page,
							@RequestParam(defaultValue="10")  int size,
							@RequestParam(required=false) String n,
							@RequestParam(required=false) String c,
							ModelMap map) {
		page = page < 1?1:page;
		Page<Customer> pageInfo = null;
		if(n == null && c == null) {
			pageInfo = customerService.findAll(page-1,size);
			map.addAttribute("pageUrl", "/admin/user");
		} 
		map.addAttribute("customers",pageInfo.getContent());
		Utils.setPageInfo4ModelMap(pageInfo, map);
		return "/biz/customer_list";
	}
	
	@RequestMapping(value="/biz/customer",method=RequestMethod.POST)
	public String customer(@ModelAttribute Customer customer) {
		
		return "redirect:/biz/customer/" + customer.getId();
	}
	
	@RequestMapping(value="/biz/customer/edit/form",method=RequestMethod.GET)
	public String customerForm(@RequestParam(defaultValue="0") int id,ModelMap map) {
		Customer customer = customerService.findById(id);
		if(customer == null)
			customer = new Customer();
		map.addAttribute("customer", customer);
		return "/biz/customer_form";
	}

	@RequestMapping(value="/biz/customer/{id}",method=RequestMethod.GET)
	public String showCustomer(@PathVariable int id,ModelMap model) {
		
		return "/biz/customer_list";
	}
}
