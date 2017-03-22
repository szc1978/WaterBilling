package org.water.billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.SysUserService;

@Controller
public class AdminUserController {
	
	@Autowired
	SysUserService sysUserService;

	@RequestMapping(value="/admin/user",method=RequestMethod.GET)
	public String user(@RequestParam(value="action",required=true) String action,
						@RequestParam(value="page",required=false) String page,
						@RequestParam(value="id",required=false) String id,
						ModelMap map) {
		String res = "/error";
		int index = (page == null) ? 0 : Integer.valueOf(page);
		switch(action) {
		case "list":
			res = "/listuser";
			Page<SysUser> users = sysUserService.findAll(index,10);
			long total = sysUserService.count();
			map.addAttribute("users",users);
			map.addAttribute("page",index);
			map.addAttribute("total",total);
			break;
		case "create":
			res = "/modifyuser";
			break;
		case "modify":
			SysUser user = sysUserService.findById(Long.valueOf(id));
			map.addAttribute("user",user);
			res = "/modifyuser";
			break;
		default:
			break;
		}
		return res;
	}
	
	@RequestMapping(value="/admin/user",method=RequestMethod.POST)
	public String user() {
		
		return "redirect:/admin/user?action=list";
	}
}
