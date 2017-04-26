package org.water.billing.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.water.billing.entity.admin.PublicAnnouncement;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.admin.PublicAnnouncementService;
import org.water.billing.utils.Utils;

@Controller
public class PublicAnnouncementController {
	
	@Autowired
	PublicAnnouncementService announcementService;

	@RequestMapping(value="/admin/publicannouncement",method=RequestMethod.GET)
	public String publicAnnouncement(ModelMap model) {
		PublicAnnouncement announcement = new PublicAnnouncement();

		model.addAttribute("announcement", announcement);
		return "/admin/publicannouncement";
	}
	
	@RequestMapping(value="/admin/publicannouncement",method=RequestMethod.POST)
	public String pubicAnnouncement(@ModelAttribute PublicAnnouncement announcement,HttpServletRequest request,ModelMap model) {
		SysUser user = Utils.getLoginUserInSession(request);
		announcement.setPublisher(user);
		announcementService.save(announcement);
		model.addAttribute("msg","发布公告成功！！！");
		return "/msg";
	}
}
