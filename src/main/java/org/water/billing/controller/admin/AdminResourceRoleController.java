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
import org.water.billing.GlobalConfigurationService;
import org.water.billing.entity.admin.SysResource;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.service.admin.SysResourceService;
import org.water.billing.service.admin.SysRoleService;

@Controller
public class AdminResourceRoleController {
	
	@Autowired
	SysResourceService resourceService;
	
	@Autowired
	SysRoleService sysRoleService;
	
	@Autowired
	GlobalConfigurationService configService;

	@RequestMapping(value="/admin/privilege",method=RequestMethod.GET)
	public String resources(ModelMap map) {
		List<SysResource> resources = resourceService.findAll();
		map.addAttribute("resources", resources);
		return "/admin/resourcerole_list";
	}
	
	@RequestMapping(value="/admin/privilege/form",method=RequestMethod.GET)
	public String form(@RequestParam(defaultValue="0") int id,ModelMap map) {
		SysResource sysResource = resourceService.findById(id);
		if(sysResource == null) 
			return "redirect:/admin/privilege";
		
		map.addAttribute("resource", sysResource);
		
		List<SysRole> sysRoles = sysRoleService.findAll();
		Collections.addAll(sysRoles);
		map.addAttribute("roles",sysRoles);
		return "/admin/resourcerole_form";
	}
	
	@RequestMapping(value="/admin/privilege",method=RequestMethod.POST)
	public String modifyResource(@ModelAttribute SysResource sysResource) {
		SysResource tmp = resourceService.findById(sysResource.getId());
		if(tmp != null) {
			tmp.setSysRoles(sysResource.getSysRoles());
			resourceService.save(tmp);
			configService.refreshResourceRoleMap();
		}
		return "redirect:/admin/privilege";
	}
	

}
