package org.water.billing.controller.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.biz.Customer;
import org.water.billing.service.biz.CustomerService;

@Controller
public class WaterDataController {
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value = "/biz/input_water_data",method=RequestMethod.GET)
	public String inputData(@RequestParam(required=false) String code,
						ModelMap map) {
		Customer customer = customerService.findByCode(code);
		if(customer == null)
			customer = new Customer();
		map.addAttribute("customer",customer);
		return "/biz/input_water_data";
	}
	
	@RequestMapping(value="/biz/input_water_data",method=RequestMethod.POST)
	public String inputData(@RequestParam int id,@RequestParam Float waterNumber) {
		Customer customer = customerService.findById(id);
		customer.setWaterNumber(waterNumber);
		Float lastWaterNumber = customer.getWaterNumber();
		customer = customerService.save(customer);
		Float currentWaterNumber = customer.getWaterNumber();

		return "redirect:/biz/input_water_data?code=" + customer.getCustomerInfo().getCode();
	}
	
	@RequestMapping(value = "/biz/import_water_data",method=RequestMethod.GET) 
	public String importData(){
		return "/biz/import_water_data";
	}

}
