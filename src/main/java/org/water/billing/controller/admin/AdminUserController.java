package org.water.billing.controller.admin;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.admin.SysRoleService;
import org.water.billing.service.admin.SysUserService;

@Controller
public class AdminUserController {
	
	@Autowired
	SysUserService sysUserService;
	
	@Autowired
	SysRoleService sysRoleService;

	@RequestMapping(value="/admin/user",method=RequestMethod.GET)
	public String user(@RequestParam(defaultValue="1") int page,
						@RequestParam(defaultValue="10")  int size,
						@RequestParam(required=false) String k,
						ModelMap map) {
		page = page < 1?1:page;
		Page<SysUser> pageInfo;
		if(k == null) {
			pageInfo = sysUserService.findAll(page-1,size);
			map.addAttribute("pageUrl", "/admin/user");
		} else {
			pageInfo = sysUserService.fuzzeFind(k, page-1, size);
			map.addAttribute("pageUrl", "/admin/user?k=" + k);
		}
		map.addAttribute("pageNum",pageInfo.getNumber() + 1);
		map.addAttribute("pageSize",pageInfo.getSize());
		map.addAttribute("totalPages",pageInfo.getTotalPages());
		map.addAttribute("isFirstPage",pageInfo.isFirst());
		map.addAttribute("isLastPage",pageInfo.isLast());
		map.addAttribute("totalCount",pageInfo.getTotalElements());
		map.addAttribute("users",pageInfo.getContent());
		return "/admin/user_list";
	}
	
	@RequestMapping(value="/admin/user/create",method=RequestMethod.GET)
	public String createUser(@RequestParam(defaultValue="0") int id,ModelMap map) {
		List<SysRole> sysRoles = sysRoleService.findAll();
		map.addAttribute("roles",sysRoles);
		SysUser sysUser = null;
		if(id == 0)
			sysUser = new SysUser();
		else
			sysUser = sysUserService.findById(id);
		Collections.addAll(sysRoles);
		map.addAttribute("sysUser",sysUser);
		return "/admin/user_create";
	}
	
	@RequestMapping(value="/admin/user",method=RequestMethod.POST)
	public String addUser(@ModelAttribute SysUser sysUser,
							@RequestParam(defaultValue="0") int resetpwd,
							ModelMap map) {
		if(resetpwd == 1 || sysUser.getId() == 0) {
			BCryptPasswordEncoder bc=new BCryptPasswordEncoder(4);
			sysUser.setPassword(bc.encode(sysUser.getPassword()));
		}
		sysUserService.save(sysUser);
		return "redirect:/admin/user";
	}
	
	@RequestMapping(value="/admin/user/modify",method=RequestMethod.GET)
	public String updateUser(@RequestParam int id,@RequestParam String action) {
		SysUser user = sysUserService.findById(id);
		if("sys".equals(user.getName()))
			return "redirect:/admin/user";
		if(user != null) {
			switch(action) {
			case "update":
				return "redirect:/admin/user/create?id=" + user.getId();
			case "enable":
				user.setActive(1);
				sysUserService.save(user);
				break;
			case "disable":
				user.setActive(0);
				sysUserService.save(user);
				break;
			default:
				break;
			}
		}
		return "redirect:/admin/user";
	}

/*	private static String getRandomString(int length) { 
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 } */ 
}
