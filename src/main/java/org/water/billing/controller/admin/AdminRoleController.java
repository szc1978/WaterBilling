package org.water.billing.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.security.support.RoleManagement;
import org.water.billing.service.admin.SysRoleService;

@Controller
public class AdminRoleController {
	
	@Autowired
	SysRoleService sysRoleService;
	
	@Autowired
	RoleManagement roleMgr;

	@RequestMapping(value="/admin/role",method=RequestMethod.GET)
	public String roleList(@RequestParam(defaultValue="0") int id,ModelMap map) {
		List<SysRole> roles = sysRoleService.findAll();
		map.addAttribute("roles", roles);
		SysRole role = null;
		if(id == 0)
			role = new SysRole();
		else
			role = sysRoleService.findById(id);
		map.addAttribute("role",role);
		return "/admin/role";
	}
	
	@RequestMapping(value="/admin/role",method=RequestMethod.POST)
	public String roleUpdate(@ModelAttribute SysRole sysRole) {
		sysRoleService.save(sysRole);
		roleMgr.refreshRoleList();
		return "redirect:/admin/role";
	}
	
	@RequestMapping(value="/admin/role/update",method=RequestMethod.GET)
	public String delRole(@RequestParam int id,@RequestParam String action) {
		SysRole role = sysRoleService.findById(id);
		if(role != null) {
			switch(action) {
				case "update":
					return "redirect:/admin/role?id=" + id;
				case "enable":
					role.setActive(1);
					sysRoleService.save(role);
					break;
				case "disable":
					role.setActive(0);
					sysRoleService.save(role);
				default:
					break;
			}
		}
		return "redirect:/admin/role";
	}
}
