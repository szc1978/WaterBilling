package org.water.billing.security.support;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.security.core.userdetails.UserDetails;  
import org.springframework.security.core.userdetails.UserDetailsService;  
import org.springframework.security.core.userdetails.UsernameNotFoundException;  
import org.springframework.stereotype.Component;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.admin.SysUserService;  
  
  
@Component  
public class CustomUserDetailsService implements UserDetailsService {  
    @Autowired  
    private SysUserService userService;  

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {  
        SysUser user = userService.findActiveUserByName(userName);  
        if (user == null) {  
            throw new UsernameNotFoundException("用户名 " + userName + " 不存在");  
        }  

        SecurityUser seu = new SecurityUser(user);  
        return  seu;  
    }  
  
}  
