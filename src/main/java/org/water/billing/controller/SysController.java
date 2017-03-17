package org.water.billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.LoginHistory;
import org.water.billing.service.LoginHistoryService;

@Controller
@RequestMapping("/sys")
public class SysController {
	
	@Autowired
	LoginHistoryService loginHistoryService;
	
	@RequestMapping("/loginhistory")
	public String addUser(@RequestParam(value="page",required=false) String page,ModelMap map) {
		int index = (page == null) ? 0 : Integer.valueOf(page);
		Page<LoginHistory> historys = loginHistoryService.findAll(index,10);
		long total = loginHistoryService.count();
		map.addAttribute("historys",historys);
		map.addAttribute("page",index);
		map.addAttribute("total",total);
		return "/loginhistory";
	}
	
}
