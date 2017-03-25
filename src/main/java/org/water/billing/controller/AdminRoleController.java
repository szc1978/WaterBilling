package org.water.billing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.service.SysRoleService;

@Controller
public class AdminRoleController {
	
	@Autowired
	SysRoleService sysRoleService;

	@RequestMapping(value="/admin/role",method=RequestMethod.GET)
	public String roleList(@RequestParam(value="id",required=false) String id,ModelMap map) {
		List<SysRole> roles = sysRoleService.findAll();
		map.addAttribute("roles", roles);
		SysRole role = null;
		if(id == null)
			role = new SysRole();
		else
			role = sysRoleService.findById(Integer.valueOf(id));
		map.addAttribute("role",role);
		return "/role";
	}
	
	@RequestMapping(value="/admin/role",method=RequestMethod.POST)
	public String roleUpdate(@ModelAttribute SysRole sysRole) {
		sysRoleService.save(sysRole);
		return "redirect:/admin/role";
	}
	
	@RequestMapping(value="/admin/role/update",method=RequestMethod.POST)
	public String delRole(@RequestParam(value="id") int id,@RequestParam(value="action") String action) {
		SysRole role = sysRoleService.findById(id);
		if(role != null) {
			switch(action) {
				case "更新":
					return "redirect:/admin/role?id=" + id;
				case "激活":
					role.setActive(1);
					sysRoleService.save(role);
					break;
				case "删除":
					role.setActive(0);
					sysRoleService.save(role);
				default:
					break;
			}
		}
		return "redirect:/admin/role";
	}
}
