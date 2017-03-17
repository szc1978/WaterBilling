
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
import org.water.billing.dao.SysUserDao;
import org.water.billing.dao.SysUserRoleDao;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.entity.admin.SysUserRole;
import org.water.billing.security.support.Appctx;
import org.water.billing.security.support.MyFilterSecurityInterceptor;  
 
@SpringBootApplication  
@EnableAutoConfiguration(exclude = MyFilterSecurityInterceptor.class)
public class MainApplication{
	
	@Autowired
	SysUserDao sysUserDao;
	
	@Autowired
	SysUserRoleDao sysUserRoleDao;

    @PostConstruct  
	public void initApplication() throws IOException { 
    	/*SysUserRole role = new SysUserRole();
    	role.setName("Admin4Test");
    	role.setRoleCode("XXXX");
    	BCryptPasswordEncoder bc=new BCryptPasswordEncoder(4);
    	String password = bc.encode("pass");
    	Set<SysUserRole> roles = new HashSet<SysUserRole>();
    	roles.add(role);
    	SysUser user = new SysUser("test", "test","aa@aa.aa", password, roles);
    	role.setSysUser(user);
    	sysUserDao.save(user);
    	sysUserRoleDao.save(role);*/
    }  
       
    public static void main(String[] args) {  
        SpringApplication app=new SpringApplication(MainApplication.class);       
        Appctx.ctx=app.run(args);  

    }   
}  