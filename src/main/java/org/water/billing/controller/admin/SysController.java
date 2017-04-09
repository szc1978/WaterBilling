package org.water.billing.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.consts.Consts;
import org.water.billing.entity.admin.LoginHistory;
import org.water.billing.entity.admin.OperationHistory;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.admin.LoginHistoryService;
import org.water.billing.service.admin.OperationHistoryService;
import org.water.billing.service.admin.SysUserService;

@Controller
public class SysController {
	
	@Autowired
	LoginHistoryService loginHistoryService;
	
	@Autowired
	OperationHistoryService opService;
	
	@Autowired
	SysUserService sysUserService;
	
	@RequestMapping("/admin/loginhistory")
	public String loginHistory(@RequestParam(defaultValue="1") int page,
								@RequestParam(defaultValue="10") int size,
								ModelMap map) {
		page = page < 1 ? 1:page;
		Page<LoginHistory> pageInfo = loginHistoryService.findAll(page-1,size);
		map.addAttribute("historys",pageInfo.getContent());
		map.addAttribute("pageNum",pageInfo.getNumber() + 1);
		map.addAttribute("pageSize",pageInfo.getSize());
		map.addAttribute("totalCount",pageInfo.getTotalElements());
		map.addAttribute("totalPages",pageInfo.getTotalPages());
		map.addAttribute("isFirstPage",pageInfo.isFirst());
		map.addAttribute("isLastPage",pageInfo.isLast());
		return "/admin/loginhistory";
	}
	
	@RequestMapping(value="/admin/oplog",method=RequestMethod.GET)
	public String opLog(@RequestParam(defaultValue="1") int page,
						@RequestParam(defaultValue="10") int size,
						ModelMap map) {
		page = page < 1 ? 1:page;
		Page<OperationHistory> pageInfo = opService.findAll(page-1,size);
		map.addAttribute("historys",pageInfo.getContent());
		map.addAttribute("pageNum",pageInfo.getNumber() + 1);
		map.addAttribute("pageSize",pageInfo.getSize());
		map.addAttribute("totalCount",pageInfo.getTotalElements());
		map.addAttribute("totalPages",pageInfo.getTotalPages());
		map.addAttribute("isFirstPage",pageInfo.isFirst());
		map.addAttribute("isLastPage",pageInfo.isLast());
		return "/admin/oplog";
	}
	
	@OpAnnotation(moduleName="系统用户配置",option="修改密码")
	@RequestMapping(value="/admin/changepassword",method=RequestMethod.POST)
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
}
