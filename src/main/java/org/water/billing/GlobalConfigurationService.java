package org.water.billing;

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
import org.water.billing.consts.Consts;
import org.water.billing.entity.admin.SysConfigurationItem;
import org.water.billing.entity.admin.SysResource;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.service.admin.SysConfigurationItemService;
import org.water.billing.service.admin.SysResourceService;
import org.water.billing.service.admin.SysRoleService;

@Service
public class GlobalConfigurationService {

	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired  
    private SysResourceService sysResoureService; 
	
	@Autowired
	private SysConfigurationItemService sysConfigService;
	
	public GlobalConfigurationService() {
		
	}
	
	//When resource_role is changed, need refresh map
	public void refreshResourceRoleMap() {
		Map<String, Collection<ConfigAttribute>> resourceRoleMap = new HashMap<String, Collection<ConfigAttribute>>();
		List<SysResource> resources = sysResoureService.findAll();
    	
    	if(resources == null)
    		return;
    	
    	for(SysResource resource : resources) {
    		Set<SysRole> roles = resource.getSysRoles();
    		if(roles == null)
    			continue;
    		String resourceString = resource.getResourceString();
    		if(!resourceRoleMap.containsKey(resourceString)) {
    			Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
    			resourceRoleMap.put(resourceString, atts);
    		}
    		for(SysRole role : roles) {
    			if(role.getActive() == Consts.STATUS_DEFINE_ACTIVE) {
	    			int rid = role.getId();
	    			ConfigAttribute ca = new SecurityConfig(String.valueOf(rid));
	    			resourceRoleMap.get(resourceString).add(ca);
    			}
    		}
    	}
    	GlobalConfiguration.getInstance().setResourceRoleMap(resourceRoleMap);
    	GlobalConfiguration.getInstance().setAllResources(resources);
	}
	
	//When add new role, need refresh role list for super user 'sys'
	public void refreshRoleList() {
		List<SysRole> allRoles = sysRoleService.findAll();
		GlobalConfiguration.getInstance().setAllRoles(allRoles);
	}
	
	//When update global configuration, need call this function
	public void refreshConfiguration() {
		List<SysConfigurationItem> configItems = sysConfigService.findAll();
		Map<String,String> configurationMap = new HashMap<String,String>();
		for(SysConfigurationItem item : configItems) {
			configurationMap.put(item.getItem(), item.getValue());
		}
		GlobalConfiguration.getInstance().setConfigurationMap(configurationMap);
	}
	
}
