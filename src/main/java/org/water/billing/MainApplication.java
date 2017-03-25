
package org.water.billing;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;  
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;  
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.water.billing.dao.SysRoleDao;
import org.water.billing.dao.SysUserDao;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.security.support.MyFilterSecurityInterceptor;  
 
@SpringBootApplication  
@EnableAutoConfiguration(exclude = MyFilterSecurityInterceptor.class)
public class MainApplication{
	
	BCryptPasswordEncoder bc=new BCryptPasswordEncoder(4);
	
	@Autowired
	SysUserDao sysUserDao;
	
	//@Autowired
	//SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	SysRoleDao sysRoleDao;
	
	public void initRole() {
		SysRole role = null;
		role = sysRoleDao.findByName("admin");
		if(role == null) {
			role = new SysRole("admin","admin","XXXX","Administrator");
			sysRoleDao.save(role);
		}
		
		role = sysRoleDao.findByName("user");
		if(role == null) {
			role = new SysRole("user","normal","AAAA","Normal user");
			sysRoleDao.save(role);
		}
	}
	
	public void initUser() {
		SysRole role = sysRoleDao.findByName("user");
		if(role == null)
			return;
		SysUser user = sysUserDao.findByName("test");
		if(user != null)
			return;
		Set<SysRole> roles = new HashSet<SysRole>();
		user = new SysUser("test", "test","test@water.com", bc.encode("pass"), roles);
		
		//roles.add(role);
		sysUserDao.save(user);
	}
	
	public void initAdmin() {
		SysRole normalRole = sysRoleDao.findByName("user");
		if(normalRole == null)
			return;
		SysRole adminRole = sysRoleDao.findByName("admin");
		if(adminRole == null)
			return;
		SysUser user = sysUserDao.findByName("admin");
		if(user != null)
			return;
		Set<SysRole> roles = new HashSet<SysRole>();
		user = new SysUser("admin", "admin","admin@water.com", bc.encode("pass"), roles);
		
		//roles.add(normalRole);
		//roles.add(adminRole);
		sysUserDao.save(user);
	}

    @PostConstruct  
	public void initApplication() throws IOException { 
    	initRole();
    	initAdmin();
    	initUser();
    }  
       
    public static void main(String[] args) {  
        SpringApplication app=new SpringApplication(MainApplication.class);       
        Appctx.ctx=app.run(args);  

    }   
}  