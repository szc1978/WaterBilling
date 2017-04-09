package org.water.billing.controller.biz;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.entity.biz.Customer;
import org.water.billing.entity.biz.CustomerWater;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.CustomerService;
import org.water.billing.service.biz.CustomerWaterService;

@Controller
public class WaterDataController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerWaterService customerWaterService;
	
	@Autowired
	BillService billService;
	
	@RequestMapping(value = "/biz/input_water_data",method=RequestMethod.GET)
	public String inputData(@RequestParam(required=false) String code,
						ModelMap map) {
		Customer customer = customerService.findByCode(code);
		if(customer == null)
			customer = new Customer();
		map.addAttribute("customer",customer);
		return "/biz/input_water_data";
	}
	
	@OpAnnotation(moduleName="数据录入",option = "录入用水量")
	@RequestMapping(value="/biz/input_water_data",method=RequestMethod.POST)
	public String inputData(@RequestParam int id,
							@RequestParam Float waterNumber,
							HttpServletRequest request) throws Exception {
		Customer customer = customerService.findById(id);
		CustomerWater customerWater = customer.getCustomerWater();
		if(waterNumber < customerWater.getOrgNumber()) {
			throw new Exception("本期用水量不应该比前期低");
		}
		
		SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		SysUser user = (SysUser) securityContext.getAuthentication().getPrincipal();
		
		customerWater.setNewNumber(waterNumber);
		customerWater.setInputerId(user.getId());
		customerWater.setInputDate(new Date());
		
		customerWaterService.save(customerWater);

		return "redirect:/biz/input_water_data";
	}
	
	@RequestMapping(value = "/biz/import_water_data",method=RequestMethod.GET) 
	public String importData(){
		return "/biz/import_water_data";
	}

}
