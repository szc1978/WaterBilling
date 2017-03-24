package org.water.billing.controller;

import java.util.Random;

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
						ModelMap map) {
		int index = (page == null) ? 0 : Integer.valueOf(page);
		Page<SysUser> users = sysUserService.findAll(index,10);
		long total = sysUserService.count();
		map.addAttribute("users",users);
		map.addAttribute("page",index);
		map.addAttribute("total",total);
		return "/user_list";
	}
	
	@RequestMapping(value="/admin/user/modify",method=RequestMethod.POST)
	public String modifyUser(@RequestParam(value="id") int id,
						ModelMap map) {
		SysUser user = sysUserService.findById(id);
		if(user == null) {
			map.addAttribute("msg", "用户不存在");
			return "/error";
		} 
		map.addAttribute("user",user);
		return "/user_modify";
	}
	
	@RequestMapping(value="/admin/user/create",method=RequestMethod.POST)
	public String createUser(ModelMap map) {
		map.addAttribute("random_password", getRandomString(6));
		return "/user_create";
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
