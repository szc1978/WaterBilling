package org.water.billing.security.support;

import java.util.ArrayList;  
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;  
import org.springframework.security.core.authority.SimpleGrantedAuthority;  
import org.springframework.security.core.userdetails.UserDetails;
import org.water.billing.GlobalConfiguration;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.entity.admin.SysUser;  
  
public class SecurityUser extends SysUser implements UserDetails {  
	private static final long serialVersionUID = -8942761076930874528L;
	
	public SecurityUser(SysUser suser) {  
        if(suser != null) {  
            this.setId(suser.getId());  
            this.setName(suser.getName());  
            this.setEmail(suser.getEmail());  
            this.setPassword(suser.getPassword());  
            this.setSysRoles(suser.getSysRoles());  
            this.setChineseName(suser.getChineseName());
            this.setCreateDate(suser.getCreateDate());
            this.setActive(suser.getActive());
            this.setWaterProvider(suser.getWaterProvider());
        }         
    }  
      
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();  
          
        if("sys".equals(this.getName())) {
        	List<SysRole> roles = GlobalConfiguration.getInstance().getAllRoles();
        	for (SysRole role : roles) {  
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(String.valueOf(role.getId()));  
                authorities.add(authority);  
            } 
        } else {
        	Set<SysRole> roles = this.getSysRoles();  
            if(roles != null) {  
                for (SysRole role : roles) {  
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(String.valueOf(role.getId()));  
                    authorities.add(authority);  
                }  
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
