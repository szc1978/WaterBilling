package org.water.billing.controller.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.consts.Consts;
import org.water.billing.entity.admin.SysRole;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.admin.SysRoleService;
import org.water.billing.service.admin.SysUserService;
import org.water.billing.utils.Utils;

@Controller
public class AdminUserController {
	
	@Autowired
	SysUserService sysUserService;
	
	@Autowired
	SysRoleService sysRoleService;

	@RequestMapping(value="/admin/user",method=RequestMethod.GET)
	public String user(@RequestParam(defaultValue="1") int page,
						@RequestParam(defaultValue="10")  int size,
						@RequestParam(required=false) String k,
						ModelMap map) {
		page = page < 1?1:page;
		Page<SysUser> pageInfo;
		if(k == null) {
			pageInfo = sysUserService.findAll(page-1,size);
			map.addAttribute("pageUrl", "/admin/user");
		} else {
			pageInfo = sysUserService.fuzzeFind(k, page-1, size);
			map.addAttribute("pageUrl", "/admin/user?k=" + k);
		}
		map.addAttribute("users",pageInfo.getContent());
		Utils.setPageInfo4ModelMap(pageInfo, map);
		return "/admin/user_list";
	}
	
	@OpAnnotation(moduleName="系统用户管理",option="修改或添加系统用户")
	@RequestMapping(value="/admin/user",method=RequestMethod.POST)
	public String addUser(@ModelAttribute SysUser sysUser,
							@RequestParam(defaultValue="0") int resetpwd,
							ModelMap map) throws Exception {
		SysUser dbUser = sysUserService.findById(sysUser.getId());
		if(Consts.SuperAdminName.equals(dbUser.getName()))
			throw new Exception("默认超级用户无法在此处修改密码，请使用 sys登录并在首页修改密码");
		
		sysUser.setName(dbUser.getName());
		if(resetpwd == 1 || sysUser.getId() == 0) {
			BCryptPasswordEncoder bc=new BCryptPasswordEncoder(4);
			if(sysUser.getPassword().length() < Consts.MIN_ADMIN_USER_PWD_LENGTH)
				throw new Exception("密码最小长度：" + Consts.MIN_ADMIN_USER_PWD_LENGTH);
			sysUser.setPassword(bc.encode(sysUser.getPassword()));
		}
		
		SysUser user = sysUserService.save(sysUser);
		return "redirect:/admin/user/" + user.getId() + "/";
	}
	
	@RequestMapping(value="/admin/user/form",method=RequestMethod.GET)
	public String createUser(@RequestParam(defaultValue="0") int id,ModelMap map) {
		List<SysRole> sysRoles = sysRoleService.findAll();
		map.addAttribute("roles",sysRoles);
		SysUser sysUser = sysUserService.findById(id);
		if(sysUser == null)
			sysUser = new SysUser();
		
		Collections.addAll(sysRoles);
		map.addAttribute("sysUser",sysUser);
		return "/admin/user_form";
	}

	@OpAnnotation(moduleName="系统用户管理",option="恢复系统用户")
	@RequestMapping(value="/admin/user/activate",method=RequestMethod.GET)
	public String active(@RequestParam int id) throws Exception {
		SysUser user = sysUserService.findById(id);
		if("sys".equals(user.getName()))
			throw new Exception("默认超级用户无法做此项操作");
		user.setActive(1);
		sysUserService.save(user);
		return "redirect:/admin/user/" + id;
	}
	
	@OpAnnotation(moduleName="系统用户管理",option="禁用系统用户")
	@RequestMapping(value="/admin/user/deactivate",method=RequestMethod.GET)
	public String deactivate(@RequestParam int id) throws Exception {
		SysUser user = sysUserService.findById(id);
		if("sys".equals(user.getName()))
			throw new Exception("默认超级用户无法做此项操作");
		user.setActive(0);
		sysUserService.save(user);
		return "redirect:/admin/user/" + id;
	}
	
	@RequestMapping(value="/admin/user/{id}",method=RequestMethod.GET)
	public String showUser(@PathVariable int id,ModelMap map) {
		SysUser user = sysUserService.findById(id);
		List<SysUser> users = new ArrayList<SysUser>();
		if(user != null)
			users.add(user);
		
		map.addAttribute("users",users);
		Utils.setPageInfo4ModelMap(null, map);
		return "/admin/user_list";
	}

}
