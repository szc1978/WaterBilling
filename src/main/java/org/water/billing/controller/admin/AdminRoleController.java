package org.water.billing.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.security.support.RoleManagement;
import org.water.billing.service.admin.SysRoleService;
import org.water.billing.utils.Utils;

@Controller
public class AdminRoleController {
	
	@Autowired
	SysRoleService sysRoleService;
	
	@Autowired
	RoleManagement roleMgr;

	@RequestMapping(value="/admin/role",method=RequestMethod.GET)
	public String roleList(@RequestParam(defaultValue="1") int page,
							@RequestParam(defaultValue="10")  int size,
							ModelMap map) {
		page = page < 1?1:page;
		Page<SysRole> pageInfo;
		pageInfo = sysRoleService.findAll(page-1,size);

		map.addAttribute("roles",pageInfo.getContent());
		Utils.setPageInfo4ModelMap(pageInfo, map);
		return "/admin/role_list";
	}
	
	@RequestMapping(value="/admin/role",method=RequestMethod.POST)
	public String roleUpdate(@ModelAttribute SysRole sysRole) {
		SysRole role = sysRoleService.save(sysRole);
		roleMgr.refreshRoleList();
		return "redirect:/admin/role/" + role.getId();
	}
	
	@RequestMapping(value="/admin/role/form",method=RequestMethod.GET)
	public String roleUpdate(@RequestParam(defaultValue="0") int id,ModelMap map) {
		SysRole role = sysRoleService.findById(id);
		if(role == null)
			role = new SysRole();
		
		map.addAttribute("role",role);
		return "/admin/role_form";
	}
	
	@RequestMapping(value="/admin/role/activate",method=RequestMethod.GET)
	public String activateRole(@RequestParam int id) {
		SysRole role = sysRoleService.findById(id);
		if(role == null)
			return "redirect:/admin/role";
		role.setActive(1);
		sysRoleService.save(role);
		return "redirect:/admin/role/" + id;
	}
	
	@RequestMapping(value="/admin/role/deactivate",method=RequestMethod.GET)
	public String deactivateRole(@RequestParam int id) {
		SysRole role = sysRoleService.findById(id);
		if(role == null)
			return "redirect:/admin/role";
		role.setActive(0);
		sysRoleService.save(role);
		return "redirect:/admin/role/" + id;
	}
	
	@RequestMapping(value="/admin/role/{id}",method=RequestMethod.GET)
	public String showUser(@PathVariable int id,ModelMap map) {
		SysRole role = sysRoleService.findById(id);
		List<SysRole> roles = new ArrayList<SysRole>();
		if(role != null)
			roles.add(role);
		
		map.addAttribute("roles",roles);
		Utils.setPageInfo4ModelMap(null, map);
		return "/admin/role_list";
	}
}
