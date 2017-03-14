package org.water.billing.security.support;

import java.util.ArrayList;  
import java.util.Collection;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import javax.annotation.PostConstruct;  
  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.security.access.ConfigAttribute;  
import org.springframework.security.access.SecurityConfig;  
import org.springframework.security.web.FilterInvocation;  
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;  
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;  
import org.springframework.security.web.util.matcher.RequestMatcher;  
import org.springframework.stereotype.Service;
import org.water.billing.entity.SysResource;
import org.water.billing.entity.SysUserRole;
import org.water.billing.service.SysResourceService;
import org.water.billing.service.SysRoleService;  
  
@Service  
public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {  
      
    @Autowired  
    private SysResourceService sysResourceService;  
      
    @Autowired  
    private SysRoleService sysRoleService;  
      
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;  
  
    @PostConstruct
    private void loadResourceDefine() {  
        List<SysUserRole> list = sysRoleService.findAll();  
        List<String> query = new ArrayList<String>();  
        if(list!=null && list.size()>0) {  
            for(SysUserRole sr :list){  
                query.add(sr.getName());  
            }  
        }   
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();  
  
        for (String auth : query) {  
            ConfigAttribute ca = new SecurityConfig(auth);  
            List<String> query1 = new ArrayList<String>();  
            List<SysResource>  list1 = sysResourceService.findByRoleName(auth);  
            if(list1!=null && list1.size()>0) {  
                for(SysResource sysResource :list1){  
                    query1.add(sysResource.getResourceString());  
                }  
            }  
            for (String res : query1) {  
                String url = res;  
                   
                if (resourceMap.containsKey(url)) {  
  
                    Collection<ConfigAttribute> value = resourceMap.get(url);  
                    value.add(ca);  
                    resourceMap.put(url, value);  
                } else {  
                    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();  
                    atts.add(ca);  
                    resourceMap.put(url, atts);  
                }  
  
            }  
        }  
  
    }  
  

    public Collection<ConfigAttribute> getAllConfigAttributes() {  
         return new ArrayList<ConfigAttribute>();  
    }  

    public Collection<ConfigAttribute> getAttributes(Object object)  
            throws IllegalArgumentException {  
        FilterInvocation filterInvocation = (FilterInvocation) object;  
        if (resourceMap == null) {  
            loadResourceDefine();  
        }  
        Iterator<String> ite = resourceMap.keySet().iterator();  
        while (ite.hasNext()) {  
            String resURL = ite.next();  
             RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);  
                if(requestMatcher.matches(filterInvocation.getHttpRequest())) {  
                return resourceMap.get(resURL);  
            }  
        }  
  
        return null;  
    }  

    public boolean supports(Class<?> arg0) {  
  
        return true;  
    }  
  
}  
