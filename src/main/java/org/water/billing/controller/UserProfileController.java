package org.water.billing.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.GlobalConfiguration;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.consts.Consts;
import org.water.billing.entity.admin.LoginHistory;
import org.water.billing.entity.admin.OperationHistory;
import org.water.billing.entity.admin.SysResource;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.admin.LoginHistoryService;
import org.water.billing.service.admin.OperationHistoryService;
import org.water.billing.service.admin.SysUserService;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.CustomerService;

@Controller
public class UserProfileController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	LoginHistoryService loginHistoryService;
	
	@Autowired
	OperationHistoryService opService;
	
	@Autowired
	BillService billService;
	
	@Autowired
	SysUserService sysUserService;

	@RequestMapping(value="/nav",method=RequestMethod.GET)
	public String nav(HttpServletRequest request,ModelMap model) {
		SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		SysUser user = (SysUser) securityContext.getAuthentication().getPrincipal();
		
		String userResources = getUserResourceList(user);
		model.addAttribute("userResources", userResources);
		return "/left";
	}
	
	@OpAnnotation(moduleName="用户配置",option="修改密码")
	@RequestMapping(value="/profile/changepassword",method=RequestMethod.POST)
	public String selfChangePassword(@RequestParam String oldPassword,
									@RequestParam String newPassword1,
									@RequestParam String newPassword2,
									HttpServletRequest request) throws Exception {
		SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		SysUser user = (SysUser) securityContext.getAuthentication().getPrincipal();
		
		BCryptPasswordEncoder bc=new BCryptPasswordEncoder(4);
		//String oldEncryptPassword = bc.encode(oldPassword);
		
		if(oldPassword == null || !bc.matches(oldPassword, user.getPassword())) 
			throw new Exception("您提供的旧密码不正确，如果忘记旧密码，请联系管理员");
		
		if(newPassword1 == null || newPassword1.length() < Consts.MIN_ADMIN_USER_PWD_LENGTH || !newPassword1.equals(newPassword2))
			throw new Exception("新密码长度不少于 "+Consts.MIN_ADMIN_USER_PWD_LENGTH+"或者两次新密码不一致！！");
			
		SysUser dbUser = sysUserService.findByName(user.getName());
		dbUser.setPassword(bc.encode(newPassword1));
		sysUserService.save(dbUser);
		return "redirect:/userprofile";
	}
	
	@RequestMapping(value="/profile/userprofile",method=RequestMethod.GET)
	public String profile(HttpServletRequest request,ModelMap model) {
		SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		SysUser user = (SysUser) securityContext.getAuthentication().getPrincipal();
		model.addAttribute("sysuser", user);
		
		LoginHistory loginHistory = loginHistoryService.findLatestByUserName(user.getName());
		model.addAttribute("loginHistory", loginHistory);
		
		List<OperationHistory> opHistory = opService.findLatest10ByUserName(user.getName());
		model.addAttribute("opHistory",opHistory);
		return "/profile/userprofile";
	}
	
	private String getUserResourceList(SysUser user) {
		String  userResourceList = "";
		for(SysResource resource : GlobalConfiguration.getInstance().getAllResources()) {
			boolean userHasRole = false;
			for(SysRole resourceRole : resource.getSysRoles()) {
				if(sysUserHasRole(user,resourceRole)) {
					userHasRole = true;
					break;
				}
			}
			if(userHasRole || Consts.SuperAdminName.equals(user.getName()))
				userResourceList += ":" + resource.getResourceString() + ":";
		}
		return userResourceList;
	}
	
	private boolean sysUserHasRole(SysUser user,SysRole role) {
		for(SysRole userRole : user.getSysRoles()) {
			if(userRole.equals(role))
				return true;
		}
		return false;
	}
}
