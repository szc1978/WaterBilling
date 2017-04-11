
package org.water.billing;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;  
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;  
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.water.billing.consts.ResourceEnum;
import org.water.billing.dao.admin.SysResourceDao;
import org.water.billing.dao.admin.SysRoleDao;
import org.water.billing.dao.admin.SysUserDao;
import org.water.billing.entity.admin.SysConfigurationItem;
import org.water.billing.entity.admin.SysResource;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.security.support.MyFilterSecurityInterceptor;
import org.water.billing.service.admin.SysConfigurationItemService;  
 
@SpringBootApplication  
@EnableAutoConfiguration(exclude = MyFilterSecurityInterceptor.class)
public class MainApplication{
	
	BCryptPasswordEncoder bc=new BCryptPasswordEncoder(4);
	
	@Autowired
	SysUserDao sysUserDao;
	
	@Autowired
	SysRoleDao sysRoleDao;
	
	@Autowired
	SysResourceDao sysResourceDao;
	
	@Autowired
	SysConfigurationItemService sysConfigService;
	
	@Autowired
	GlobalConfigurationService configService;
	
	private void initRole() {
		SysRole role = null;
		role = sysRoleDao.findByName("系统管理员");
		if(role == null) {
			role = new SysRole("系统管理员","admin","XXXX","Administrator");
			sysRoleDao.save(role);
		}
	}
	
	private void initSuper() {
		SysUser user = sysUserDao.findByName("sys");
		if(user != null)
			return;
		Set<SysRole> roles = new HashSet<SysRole>();
		user = new SysUser("sys", "超级管理员","sys@water.com", bc.encode("pass"), roles);
		
		sysUserDao.save(user);
	}
	
	private void initResource() {
		Set<SysRole> sysRoles = new HashSet<SysRole>();
		for(ResourceEnum resourceType : ResourceEnum.values()) {
			SysResource resource = sysResourceDao.findByName(resourceType.getName());
			if(resource == null) {
				resource = new SysResource(resourceType.getName(),resourceType.getResourceString(),sysRoles);
				sysResourceDao.save(resource);
			}
		}
	}
	
	private void initDefaultGlobalConfig() {
		Map<String,String> defaultGlobalConfig = new HashMap<String,String>();
		defaultGlobalConfig.put("late_pay_day", "50");
		defaultGlobalConfig.put("late_pay_ratio","0.05");
		defaultGlobalConfig.put("disable_approve_customer", "0");
		defaultGlobalConfig.put("disable_approve_customer_water", "0");
		defaultGlobalConfig.put("disable_approve_customer_bill", "0");
		for(String key : defaultGlobalConfig.keySet()) {
			SysConfigurationItem item = sysConfigService.findItem(key);
			if(item != null)
				continue;
			item = new SysConfigurationItem(key,defaultGlobalConfig.get(key));
			sysConfigService.save(item);
		}
	}

    @PostConstruct  
	public void initApplication() throws IOException { 
    	initRole();
    	initSuper();
    	initResource();
    	initDefaultGlobalConfig();
    	
    	initGlobalConfiguration();
    } 
    
    private void initGlobalConfiguration() {
    	configService.refreshConfiguration();
    	configService.refreshRoleList();
    	configService.refreshResourceRoleMap();
    }
       
    public static void main(String[] args) {  
        SpringApplication app=new SpringApplication(MainApplication.class);       
        Appctx.ctx=app.run(args);  

    }   
}  