package org.water.billing.controller.biz;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.biz.Charge;
import org.water.billing.entity.biz.Customer;
import org.water.billing.entity.biz.CustomerType;
import org.water.billing.service.biz.ChargeService;
import org.water.billing.service.biz.CustomerTypeService;

@Controller
public class CustomerTypeController {
	
	@Autowired
	CustomerTypeService customerTypeService;
	
	@Autowired
	ChargeService chargeService;

	@RequestMapping(value="/biz/customer_type",method=RequestMethod.GET)
	public String customer(ModelMap map) {
		List<CustomerType> customerTypes = customerTypeService.findAll();
		map.addAttribute("customerTypes",customerTypes);
		return "/biz/customer_type_list";
	}
	
	@RequestMapping(value="/biz/customer_type",method=RequestMethod.POST)
	public String customer(@ModelAttribute CustomerType customerType) {
		customerTypeService.save(customerType);
		return "redirect:/biz/customer_type";
	}
	
	@RequestMapping(value="/biz/customer_type/edit/form",method=RequestMethod.GET)
	public String customerForm(@RequestParam(defaultValue="0") int id,ModelMap map) {
		List<Charge> charges = chargeService.findAll();
		map.addAttribute("all_charges", charges);
		
		CustomerType customerType = customerTypeService.findById(id);
		if(customerType == null)
			customerType = new CustomerType();
		Collections.addAll(charges);
		map.addAttribute("customerType", customerType);
		return "/biz/customer_type_form";
	}
	
	@RequestMapping(value="/biz/customer_type/list_charge",method=RequestMethod.GET)
	public String listCustomerTypeCharge(@RequestParam int id,ModelMap map) {
		CustomerType customerType = customerTypeService.findById(id);
		map.addAttribute("charges",customerType.getCharges());
		return "/biz/customer_type_list_charge";
	}

}
