package org.water.billing.security.support;

import java.util.ArrayList;  
import java.util.Collection;  
import java.util.Iterator;  
import java.util.Map;
import org.springframework.security.access.ConfigAttribute;  
import org.springframework.security.web.FilterInvocation;  
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;  
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;  
import org.springframework.security.web.util.matcher.RequestMatcher;  
import org.springframework.stereotype.Service;
import org.water.billing.GlobalConfiguration;  
  
@Service  
public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {  
      
    //@Autowired  
    //GlobalConfigurationService globalConfig;  
      
    //private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
  
    public Collection<ConfigAttribute> getAllConfigAttributes() {  
         return new ArrayList<ConfigAttribute>();  
    }  

    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {  
        FilterInvocation filterInvocation = (FilterInvocation) object;  
        
        Map<String, Collection<ConfigAttribute>> resourceMap = GlobalConfiguration.getInstance().getResourceRoleMap();
        
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
