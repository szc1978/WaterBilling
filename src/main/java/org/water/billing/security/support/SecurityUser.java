package org.water.billing.security.support;

import java.util.ArrayList;  
import java.util.Collection;  
import java.util.Set;  
  
import org.springframework.security.core.GrantedAuthority;  
import org.springframework.security.core.authority.SimpleGrantedAuthority;  
import org.springframework.security.core.userdetails.UserDetails;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.entity.admin.SysUserRole;  
  
public class SecurityUser extends SysUser implements UserDetails {  
	private static final long serialVersionUID = -8942761076930874528L;

	public SecurityUser(SysUser suser) {  
        if(suser != null) {  
            this.setId(suser.getId());  
            this.setName(suser.getName());  
            this.setEmail(suser.getEmail());  
            this.setPassword(suser.getPassword());  
            this.setSysUserRoles(suser.getSysUserRoles());  
            this.setChineseName(suser.getChineseName());
            this.setCreateDate(suser.getCreateDate());
            this.setActive(suser.getActive());
        }         
    }  
      
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();  
        Set<SysUserRole> userRoles = this.getSysUserRoles();  
          
        if(userRoles != null) {  
            for (SysUserRole userRole : userRoles) {  
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(String.valueOf(userRole.getRid()));  
                authorities.add(authority);  
            }  
        }  
        return authorities;  
    }  
  
    @Override  
    public String getPassword() {  
        return super.getPassword();  
    }  
   
    public String getUsername() {  
        return super.getName();  
    }  
  
    public boolean isAccountNonExpired() {  
        return true;  
    }  
 
    public boolean isAccountNonLocked() {  
        return true;  
    }  
   
    public boolean isCredentialsNonExpired() {  
        return true;  
    }  
   
    public boolean isEnabled() {  
        return true;  
    }  
}  
