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
import org.water.billing.MyException;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.consts.ChargeTypeEnum;
import org.water.billing.entity.biz.Charge;
import org.water.billing.entity.biz.CustomerType;
import org.water.billing.service.biz.ChargeService;
import org.water.billing.service.biz.CustomerTypeService;

@Controller
public class CustomerTypeController {
	
	@Autowired
	CustomerTypeService customerTypeService;
	
	@Autowired
	ChargeService chargeService;

	@RequestMapping(value="/customer/type",method=RequestMethod.GET)
	public String customer(@RequestParam(defaultValue="0") int id,ModelMap map) {
		List<CustomerType> customerTypes = customerTypeService.findAll();
		map.addAttribute("customerTypes",customerTypes);
		CustomerType customerType = null;
		for(CustomerType c : customerTypes) {
			if(c.getId() == id) {
				customerType = c;
				break;
			}
		}
		if(customerType == null)
			customerType = new CustomerType();
		map.addAttribute("type",customerType);
		return "/customer/customer_type_list";
	}
	
	@OpAnnotation(moduleName="客户类型管理",option="增加或修改用户类型")
	@RequestMapping(value="/customer/type",method=RequestMethod.POST)
	public String customer(@ModelAttribute CustomerType customerType) throws MyException {
		boolean conflictFlag = false;
		for(Charge charge : customerType.getCharges()) {
			if(charge.getChargeType() == ChargeTypeEnum.CHARGE_STEP_BY_STEP.getId() 
					|| charge.getChargeType() == ChargeTypeEnum.CHARGE_BY_BOTTOM.getId()) {
				if(conflictFlag)
					throw new MyException("保底费率和阶梯费率只能出现一次");
				conflictFlag = true;
			}
		}
		customerTypeService.save(customerType);
		return "redirect:/customer/type";
	}
	
	@RequestMapping(value="/customer/type/form",method=RequestMethod.GET)
	public String customerForm(@RequestParam(defaultValue="0") int id,ModelMap map) {
		List<Charge> charges = chargeService.findAll();
		map.addAttribute("all_charges", charges);
		
		CustomerType customerType = customerTypeService.findById(id);
		if(customerType == null)
			customerType = new CustomerType();
		Collections.addAll(charges);
		map.addAttribute("customerType", customerType);
		return "/customer/customer_type_form";
	}

}
