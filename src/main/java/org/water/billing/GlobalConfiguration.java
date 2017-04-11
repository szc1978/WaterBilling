package org.water.billing;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.water.billing.entity.admin.SysResource;
import org.water.billing.entity.admin.SysRole;

public class GlobalConfiguration {
	private static GlobalConfiguration instance = null;
	
	private Map<String,String> configurationMap = null;
	private List<SysRole> allRoles = null;
	private Map<String, Collection<ConfigAttribute>> resourceRoleMap = null;
	private List<SysResource> allResources = null;
	
	private GlobalConfiguration() {
		
	}
	
	public static GlobalConfiguration getInstance() {
		if(instance == null) {
			instance = new GlobalConfiguration();
		}
		return instance;
	}

	public void setConfigurationMap(Map<String,String> configurationMap) {
		this.configurationMap = configurationMap;
	}
	
	public void setAllRoles(List<SysRole> allRoles) {
		this.allRoles = allRoles;
	}
	
	public void setResourceRoleMap(Map<String, Collection<ConfigAttribute>> resourceRoleMap) {
		this.resourceRoleMap = resourceRoleMap;
	}

	public Map<String, String> getConfigurationMap() {
		return configurationMap;
	}

	public List<SysRole> getAllRoles() {
		return allRoles;
	}

	public Map<String, Collection<ConfigAttribute>> getResourceRoleMap() {
		return resourceRoleMap;
	}
	
	public List<SysResource> getAllResources() {
		return allResources;
	}
	
	public void setAllResources(List<SysResource> allResources) {
		this.allResources = allResources;
	}
	
	public String getConfigValueByItemName(String itemName) {
		return configurationMap.get(itemName);
	}
}
