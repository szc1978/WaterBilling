package org.water.billing.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.admin.LoginHistory;
import org.water.billing.entity.admin.OperationHistory;
import org.water.billing.service.admin.LoginHistoryService;
import org.water.billing.service.admin.OperationHistoryService;

@Controller
public class LogController {
	
	@Autowired
	LoginHistoryService loginHistoryService;
	
	@Autowired
	OperationHistoryService opService;

	@RequestMapping("/log/login")
	public String loginHistory(@RequestParam(defaultValue="1") int page,
								@RequestParam(defaultValue="10") int size,
								ModelMap map) {
		page = page < 1 ? 1:page;
		Page<LoginHistory> pageInfo = loginHistoryService.findAll(page-1,size);
		map.addAttribute("historys",pageInfo.getContent());
		map.addAttribute("pageNum",pageInfo.getNumber() + 1);
		map.addAttribute("pageSize",pageInfo.getSize());
		map.addAttribute("totalCount",pageInfo.getTotalElements());
		map.addAttribute("totalPages",pageInfo.getTotalPages());
		map.addAttribute("isFirstPage",pageInfo.isFirst());
		map.addAttribute("isLastPage",pageInfo.isLast());
		return "/log/loginhistory";
	}
	
	@RequestMapping(value="/log/operation",method=RequestMethod.GET)
	public String opLog(@RequestParam(defaultValue="1") int page,
						@RequestParam(defaultValue="10") int size,
						ModelMap map) {
		page = page < 1 ? 1:page;
		Page<OperationHistory> pageInfo = opService.findAll(page-1,size);
		map.addAttribute("historys",pageInfo.getContent());
		map.addAttribute("pageNum",pageInfo.getNumber() + 1);
		map.addAttribute("pageSize",pageInfo.getSize());
		map.addAttribute("totalCount",pageInfo.getTotalElements());
		map.addAttribute("totalPages",pageInfo.getTotalPages());
		map.addAttribute("isFirstPage",pageInfo.isFirst());
		map.addAttribute("isLastPage",pageInfo.isLast());
		return "/log/oplog";
	}
	
	
}
