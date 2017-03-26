package org.water.billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.LoginHistory;
import org.water.billing.service.LoginHistoryService;

@Controller
public class SysController {
	
	@Autowired
	LoginHistoryService loginHistoryService;
	
	@RequestMapping("/admin/loginhistory")
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
		return "/loginhistory";
	}
	
	@RequestMapping(value="/admin/oplog",method=RequestMethod.GET)
	public String opLog(@RequestParam(value="page",required=false) String page,ModelMap map) {
		
		return "/oplog";
	}
}
