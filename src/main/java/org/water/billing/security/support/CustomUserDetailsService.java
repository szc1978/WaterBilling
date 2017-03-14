package org.water.billing.security.support;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.security.core.userdetails.UserDetails;  
import org.springframework.security.core.userdetails.UserDetailsService;  
import org.springframework.security.core.userdetails.UsernameNotFoundException;  
import org.springframework.stereotype.Component;
import org.water.billing.entity.SysUser;
import org.water.billing.service.SysUserService;  
  
  
@Component  
public class CustomUserDetailsService implements UserDetailsService {  
    @Autowired  
    private SysUserService userService;  

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {  
        SysUser user = userService.findByName(userName);  
        if (user == null) {  
            throw new UsernameNotFoundException("UserName " + userName + " not found");  
        }  

        SecurityUser seu = new SecurityUser(user);  
        return  seu;  
    }  
  
}  
