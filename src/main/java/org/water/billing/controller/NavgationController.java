package org.water.billing.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.water.billing.GlobalConfiguration;
import org.water.billing.consts.Consts;
import org.water.billing.consts.ResourceEnum;
import org.water.billing.entity.admin.LoginHistory;
import org.water.billing.entity.admin.OperationHistory;
import org.water.billing.entity.admin.SysResource;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.admin.LoginHistoryService;
import org.water.billing.service.admin.OperationHistoryService;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.CustomerService;

@Controller
public class NavgationController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	LoginHistoryService loginHistoryService;
	
	@Autowired
	OperationHistoryService opService;
	
	@Autowired
	BillService billService;

	@RequestMapping(value="/nav",method=RequestMethod.GET)
	public String nav(HttpServletRequest request,ModelMap model) {
		SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		SysUser user = (SysUser) securityContext.getAuthentication().getPrincipal();
		
		int pendingMsgNumber = 0;
		if(sysUserHasCustomerApproverPri(user) || Consts.SuperAdminName.equals(user.getName())) {
			pendingMsgNumber = customerService.findPenging4ApproveMsg();
			pendingMsgNumber += billService.findPendingBillNumber();
		}
		model.addAttribute("msgNumber", pendingMsgNumber);
		
		String userResources = getUserResourceList(user);
		model.addAttribute("userResources", userResources);
		return "/left";
	}
	
	@RequestMapping(value="/userprofile",method=RequestMethod.GET)
	public String profile(HttpServletRequest request,ModelMap model) {
		SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		SysUser user = (SysUser) securityContext.getAuthentication().getPrincipal();
		model.addAttribute("sysuser", user);
		
		int pendingMsgNumber = 0;
		if(sysUserHasCustomerApproverPri(user) || Consts.SuperAdminName.equals(user.getName())) {
			pendingMsgNumber = customerService.findPenging4ApproveMsg();
			pendingMsgNumber += billService.findPendingBillNumber();
		}
		model.addAttribute("msgNumber", pendingMsgNumber);
		
		LoginHistory loginHistory = loginHistoryService.findLatestByUserName(user.getName());
		model.addAttribute("loginHistory", loginHistory);
		
		List<OperationHistory> opHistory = opService.findLatest10ByUserName(user.getName());
		model.addAttribute("opHistory",opHistory);
		return "/admin/userprofile";
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
	
	private boolean sysUserHasCustomerApproverPri(SysUser user) {
		for(SysResource resource : GlobalConfiguration.getInstance().getAllResources()) {
			if(! resource.getName().equals(ResourceEnum.BIZ_CUSTOMER_APPROVER.getResourceString()))
				continue;
			for(SysRole resourceRole : resource.getSysRoles()) {
				if(sysUserHasRole(user,resourceRole)) 
					return true;
			}
		}
		return false;
	}
	
	private boolean sysUserHasRole(SysUser user,SysRole role) {
		for(SysRole userRole : user.getSysRoles()) {
			if(userRole.equals(role))
				return true;
		}
		return false;
	}
}
