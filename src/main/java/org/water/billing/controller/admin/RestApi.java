package org.water.billing.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.water.billing.GlobalConfiguration;
import org.water.billing.consts.Consts;
import org.water.billing.consts.ResourceEnum;
import org.water.billing.entity.admin.SysResource;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.admin.SysUserService;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.CustomerService;

@RestController
public class RestApi {

	@Autowired
	SysUserService sysUserService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	BillService billService;
	
	@RequestMapping(value="/api/getuserbyname",method=RequestMethod.GET)
	public int getUserByName(@RequestParam(value="name",required=true) String name) {
		SysUser user = sysUserService.findByName(name);
		if(user == null)
			return 0;
		return 1;
	}
	
	@RequestMapping(value="/api/getpendingmsg",method=RequestMethod.GET)
	public int getPendingMsgNum(HttpServletRequest request) {
		SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		SysUser user = (SysUser) securityContext.getAuthentication().getPrincipal();
		
		int pendingMsgNumber = 0;
		if(sysUserHasCustomerApproverPri(user) || Consts.SuperAdminName.equals(user.getName())) {
			pendingMsgNumber = customerService.findPenging4ApproveMsg();
			pendingMsgNumber += billService.findPendingBillNumber();
		}
		return pendingMsgNumber;
	}
	
	private boolean sysUserHasCustomerApproverPri(SysUser user) {
		for(SysResource resource : GlobalConfiguration.getInstance().getAllResources()) {
			if(! resource.getName().equals(ResourceEnum.APPROVE_CUSTOMER.getResourceString())
					&& !resource.getName().equals(ResourceEnum.APPROVE_CUSTOMER_WATER.getResourceString())
					&& !resource.getName().equals(ResourceEnum.APPROVE_CUSTOMER_BILL.getResourceString())
					)
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
