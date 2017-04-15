package org.water.billing.controller.rest;

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
	

	
	
}
