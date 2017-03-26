package org.water.billing.controller.admin;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.admin.SysResource;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.security.support.RoleManagement;
import org.water.billing.service.admin.SysResourceService;
import org.water.billing.service.admin.SysRoleService;

@Controller
public class AdminResourceRoleController {
	
	@Autowired
	SysResourceService resourceService;
	
	@Autowired
	SysRoleService sysRoleService;
	
	@Autowired
	RoleManagement roleMgr;

	@RequestMapping(value="/admin/privilege",method=RequestMethod.GET)
	public String resources(@RequestParam(defaultValue="0") int id,ModelMap map) {
		List<SysResource> resources = resourceService.findAll();
		map.addAttribute("resources", resources);
		List<SysRole> sysRoles = sysRoleService.findAll();
		map.addAttribute("roles",sysRoles);
		SysResource sysResource = null;
		if(id != 0) 
			sysResource = resourceService.findById(id);
		else
			sysResource = new SysResource();
		Collections.addAll(sysRoles);
		map.addAttribute("sysResource",sysResource);
		return "/resourcerole";
	}
	
	@RequestMapping(value="/admin/privilege/modifyresource",method=RequestMethod.POST)
	public String modifyResource(@ModelAttribute SysResource sysResource) {
		SysResource tmp = resourceService.findById(sysResource.getId());
		if(tmp != null) {
			resourceService.save(sysResource);
			roleMgr.refreshResourceRoleMap();
		}
		return "redirect:/admin/privilege";
	}
	

}
