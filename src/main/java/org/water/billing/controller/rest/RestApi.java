package org.water.billing.controller.rest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.water.billing.entity.biz.Customer;
import org.water.billing.service.biz.CustomerService;

@RestController
@RequestMapping(value="/rest")
public class RestApi {
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value="/customer",method=RequestMethod.GET)
	public JSONObject getCustomerInformation(@RequestParam String code) {
		Customer customer = customerService.findByCode(code);
		if(customer == null)
			customer = new Customer();
		return customer.toJson();
	}
	
	
}
