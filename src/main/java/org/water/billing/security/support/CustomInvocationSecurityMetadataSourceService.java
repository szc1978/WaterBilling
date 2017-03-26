package org.water.billing.security.support;

import java.util.ArrayList;  
import java.util.Collection;  
import java.util.Iterator;  
import java.util.Map;
import javax.annotation.PostConstruct;  
  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.security.access.ConfigAttribute;  
import org.springframework.security.web.FilterInvocation;  
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;  
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;  
import org.springframework.security.web.util.matcher.RequestMatcher;  
import org.springframework.stereotype.Service;  
  
@Service  
public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {  
      
    @Autowired  
    RoleManagement roleMgr;  
      
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
  
    @PostConstruct
    private void loadResourceDefine() {
    	roleMgr.initial();
    	resourceMap = RoleManagement.Resource_Role_Map;
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
