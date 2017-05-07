package org.water.billing.controller.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.water.billing.GlobalConfiguration;
import org.water.billing.biz.DataCache;
import org.water.billing.consts.Consts;
import org.water.billing.consts.ResourceEnum;
import org.water.billing.entity.admin.SysResource;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.admin.SysUserService;
import org.water.billing.utils.Utils;

@RestController
public class InternalApi {
	@Autowired
	SysUserService sysUserService;
	
	@RequestMapping(value="/api/getuserbyname",method=RequestMethod.GET)
	public int getUserByName(@RequestParam(value="name",required=true) String name) {
		SysUser user = sysUserService.findByName(name);
		if(user == null)
			return 0;
		return 1;
	}
	
	@RequestMapping(value="/api/getpendingmsg",method=RequestMethod.GET)
	public String getPendingMsgNum(HttpServletRequest request) {
		SysUser user = Utils.getLoginUserInSession(request);
		int countPendingCustomer = sysUserHasCustomerApproverPri(user,ResourceEnum.APPROVE_CUSTOMER) 
				? DataCache.getInstance().getPendingCountCustomerStatus() : 0;
				
		int countPendingCustomerWater = sysUserHasCustomerApproverPri(user,ResourceEnum.APPROVE_CUSTOMER_WATER)
				? DataCache.getInstance().getPendingCountCustomerWater() : 0;
		
		int countPendingBill = sysUserHasCustomerApproverPri(user,ResourceEnum.APPROVE_CUSTOMER_BILL)
				? DataCache.getInstance().getPendingCountCustomerBill() : 0;
		
		int total = countPendingCustomer + countPendingCustomerWater + countPendingBill;
		String msg = total + ":" + countPendingCustomer + ":" + countPendingCustomerWater + ":" + countPendingBill;
		return msg;
	}
	
	private boolean sysUserHasCustomerApproverPri(SysUser user,ResourceEnum resourceType) {
		if(Consts.SuperAdminName.equals(user.getName()))
			return true;
		for(SysResource resource : GlobalConfiguration.getInstance().getAllResources()) {
			if(! resource.getName().equals(resourceType.getResourceString()))
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
