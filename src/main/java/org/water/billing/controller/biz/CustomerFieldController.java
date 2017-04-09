package org.water.billing.controller.biz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerFieldController {

	@RequestMapping(value="/biz/customer_field",method=RequestMethod.GET)
	public String customerField() {
		
		return "/biz/customer_field";
	}
}
