package org.water.billing.controller.biz;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.consts.Consts;
import org.water.billing.consts.ReadMeterCycleEnum;
import org.water.billing.entity.biz.Customer;
import org.water.billing.entity.biz.CustomerType;
import org.water.billing.entity.biz.WaterProvider;
import org.water.billing.service.biz.CustomerService;
import org.water.billing.service.biz.CustomerTypeService;
import org.water.billing.service.biz.WaterProviderService;
import org.water.billing.utils.Utils;

@Controller
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerTypeService customerTypeService;
	
	@Autowired
	WaterProviderService waterProviderService;

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
			map.addAttribute("pageUrl", "/biz/customer");
		} 
		map.addAttribute("customers",pageInfo.getContent());
		Utils.setPageInfo4ModelMap(pageInfo, map);
		return "/biz/customer_list";
	}
	
	@RequestMapping(value="/biz/customer",method=RequestMethod.POST)
	public String customer(@ModelAttribute Customer customer) {
		
		customerService.save(customer);
		return "redirect:/biz/customer/";
	}
	
	@RequestMapping(value="/biz/customer/edit/form",method=RequestMethod.GET)
	public String customerForm(@RequestParam(defaultValue="0") int id,ModelMap map) {
		Customer customer = customerService.findById(id);
		if(customer == null)
			customer = new Customer();
		map.addAttribute("customer", customer);
		map.addAttribute("certificateNames",Consts.CUSTOMER_CERTIFICATE_TYPE);
		List<CustomerType> customerTypes = customerTypeService.findAll();
		map.addAttribute("customerTypes",customerTypes);
		List<WaterProvider> waterProviders = waterProviderService.findAll();
		map.addAttribute("waterProviders",waterProviders);
		map.addAttribute("readMeterCycles",getReadMeterCycleList());
		return "/biz/customer_form";
	}

	@RequestMapping(value="/biz/customer/{id}",method=RequestMethod.GET)
	public String showCustomer(@PathVariable int id,ModelMap model) {
		
		return "/biz/customer_list";
	}
	
	private List<String> getReadMeterCycleList() {
		List<String> readMeterCycles = new ArrayList<String>();
		for(ReadMeterCycleEnum cycle : ReadMeterCycleEnum.values())
			readMeterCycles.add(cycle.getName());
		return readMeterCycles;
	}
}
