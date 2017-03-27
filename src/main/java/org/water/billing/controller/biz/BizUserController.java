package org.water.billing.controller.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.water.billing.service.admin.LoginHistoryService;

@Controller
public class BizUserController {
	
	@Autowired
	LoginHistoryService loginHistoryService;
	
	@RequestMapping("/biz/user")
	public String bizuser() {

		return "/biz/userprofile";
	}

}
