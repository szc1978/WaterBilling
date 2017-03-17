package org.water.billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.SysUserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	SysUserService sysUserService;

	@RequestMapping("/role")
	public String listRole(ModelMap map) {
		
		return "/managesysrole";
	}
	
	@RequestMapping("/user")
	public String addUser(@RequestParam(value="page",required=false) String page,ModelMap map) {
		int index = (page == null) ? 0 : Integer.valueOf(page);
		Page<SysUser> users = sysUserService.findAll(index,10);
		long total = sysUserService.count();
		map.addAttribute("users",users);
		map.addAttribute("page",index);
		map.addAttribute("total",total);
		return "/listuser";
	}
	
	@RequestMapping("/modifyuser")
	public String modifyUser(@RequestParam(value="id") long id,ModelMap map) {
		SysUser user = sysUserService.findById(id);
		map.addAttribute("user",user);
		return "/modifyuser";
	}
}
