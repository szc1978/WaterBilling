package org.water.billing.security.support;

import java.io.IOException;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;  
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.water.billing.dao.LoginHistoryDao;
import org.water.billing.entity.LoginHistory;
import org.water.billing.entity.admin.SysUser;  
  
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {  
	@Autowired
	LoginHistoryDao loginLogDao;
	
    @Override    
    public void onAuthenticationSuccess(HttpServletRequest request,    
            HttpServletResponse response, Authentication authentication) throws IOException,    
            ServletException {    
        SysUser userDetails = (SysUser)authentication.getPrincipal();  
        LoginHistory login = new LoginHistory(userDetails.getName(),userDetails.getChineseName(),getIpAddress(request));
        loginLogDao.save(login);
        super.onAuthenticationSuccess(request, response, authentication);    
    }    
      
    public String getIpAddress(HttpServletRequest request){      
        String ip = request.getHeader("x-forwarded-for");      
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getHeader("Proxy-Client-IP");      
        }      
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getHeader("WL-Proxy-Client-IP");      
        }      
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getHeader("HTTP_CLIENT_IP");      
        }      
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");      
        }      
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getRemoteAddr();      
        }      
        return ip;      
    }    
} 
