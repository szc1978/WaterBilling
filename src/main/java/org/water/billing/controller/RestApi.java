package org.water.billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.SysUserService;

@RestController
public class RestApi {

	@Autowired
	SysUserService sysUserService;
	
	@RequestMapping(value="/api/getuserbyname",method=RequestMethod.GET)
	public int getUserByName(@RequestParam(value="name",required=true) String name) {
		SysUser user = sysUserService.findByName(name);
		if(user == null)
			return 0;
		return 1;
	}
}
