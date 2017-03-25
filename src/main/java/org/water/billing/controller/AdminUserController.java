package org.water.billing.controller;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.SysRoleService;
import org.water.billing.service.SysUserService;

@Controller
public class AdminUserController {
	
	@Autowired
	SysUserService sysUserService;
	
	@Autowired
	SysRoleService sysRoleService;

	@RequestMapping(value="/admin/user",method=RequestMethod.GET)
	public String user(@RequestParam(value="page",required=false) String page,
						ModelMap map) {
		int index = (page == null) ? 0 : Integer.valueOf(page);
		Page<SysUser> users = sysUserService.findAll(index,10);
		long total = sysUserService.count();
		map.addAttribute("users",users);
		map.addAttribute("page",index);
		map.addAttribute("total",total);
		return "/user_list";
	}
	
	@RequestMapping(value="/admin/user/create",method=RequestMethod.POST)
	public String createUser(@RequestParam(value="id",required=false) String id,ModelMap map) {
		List<SysRole> sysRoles = sysRoleService.findAll();
		map.addAttribute("roles",sysRoles);
		SysUser sysUser = null;
		if(id == null)
			sysUser = new SysUser();
		else
			sysUser = sysUserService.findById(Integer.valueOf(id));
		Collections.addAll(sysRoles);
		map.addAttribute("user",sysUser);
		return "/user_create";
	}
	
	@RequestMapping(value="/admin/user",method=RequestMethod.POST)
	public String addUser(@ModelAttribute SysUser sysUser,ModelMap map) {
		sysUserService.save(sysUser);
		return "redirect:/admin/user";
	}

	private static String getRandomString(int length) { 
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }  
}
