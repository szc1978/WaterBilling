package org.water.billing.security.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.water.billing.entity.admin.SysResource;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.service.admin.SysResourceService;
import org.water.billing.service.admin.SysRoleService;

@Service
public class RoleManagement {
	public static List<SysRole> All_Roles = null;
	public static Map<String, Collection<ConfigAttribute>> Resource_Role_Map = null;
	
	@Autowired
	SysRoleService sysRoleService;
	
	@Autowired  
    private SysResourceService sysResoureService; 
	
	public void initial() {
		refreshResourceRoleMap();
		refreshRoleList();
	}
	
	//When resource_role is changed, need refresh map
	public void refreshResourceRoleMap() {
		Resource_Role_Map = new HashMap<String, Collection<ConfigAttribute>>();
		List<SysResource> resources = sysResoureService.findAll();
    	
    	if(resources == null)
    		return;
    	
    	for(SysResource resource : resources) {
    		Set<SysRole> roles = resource.getSysRoles();
    		if(roles == null)
    			continue;
    		String resourceString = resource.getResourceString();
    		if(!Resource_Role_Map.containsKey(resourceString)) {
    			Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
    			Resource_Role_Map.put(resourceString, atts);
    		}
    		for(SysRole role : roles) {
    			int rid = role.getId();
    			ConfigAttribute ca = new SecurityConfig(String.valueOf(rid));
    			Resource_Role_Map.get(resourceString).add(ca);
    		}
    	}
	}
	
	//When add new role, need refresh role list for super user 'sys'
	public void refreshRoleList() {
		All_Roles = sysRoleService.findAll();
	}
}
