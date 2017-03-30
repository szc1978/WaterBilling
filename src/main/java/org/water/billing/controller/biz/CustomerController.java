package org.water.billing.controller.biz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerController {

	@RequestMapping("/biz/customer")
	public String bizuser() {

		return "/biz/customer";
	}

}
